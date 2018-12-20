package com.incar.synchronize;

import com.incar.entity.AGps;
import com.incar.entity.AVehicle;
import com.incar.entity.ObdLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 同步mysql数据源
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/17 13:56
 */
@Component
public class MysqlSync {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate2;

    @Value("${time}")
    private int time;

    @Value("${ids}")
    private long ids;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void scheduled() {
        try {
            Date data = new Date();
            long pre = data.getTime() - time * 60 * 1000;
            Date dataPre = new Date(pre);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateString = sDateFormat.format(data);
            String preString = sDateFormat.format(dataPre);
            List<ObdLocation> obdLocationList = queryForList(ids, preString, dateString);
            insertList(obdLocationList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取源数据库 数据 根据时间，id 获取；
    public List<ObdLocation> queryForList(long id, String recordTime, String recordTimeEnd) throws DataAccessException, Exception {
        String sql = "select obdCode,tripId,vid, vin, locationSpeed, travelDistance,  longitude, latitude, direction, locationTime, locationType, recordTime from t_obd_location  where id >  ? and recordTime >=? and recordTime < ?";
        System.out.println("开始同步： 时间：" + recordTimeEnd + " SQL: " + sql + "   " + id + "   " + recordTime + "  " +recordTimeEnd);
        Object args[] = new Object[]{id, recordTime, recordTimeEnd};
        List<ObdLocation> obdLocationList = jdbcTemplate1.query(sql, args, new BeanPropertyRowMapper(ObdLocation.class));
        return obdLocationList;
    }

    // 写入数据库
    public void insertList(List<ObdLocation> locationList) {
        List<AGps> gpsList = new ArrayList<>();
        List<AVehicle> vehicleList = new ArrayList<>();

        locationList.forEach(a ->  {
            AGps gps = new AGps();
            AVehicle aVehicle = new AVehicle();
            gps.setGprscode(a.getObdCode());
            gps.setLat(Double.parseDouble(a.getLatitude().substring(1)));
            gps.setLng(Double.parseDouble(a.getLongitude().substring(1)));
            aVehicle.setGprscode(a.getObdCode());
            aVehicle.setPlateNo("鄂A" + a.getVid());
            gpsList.add(gps);
            vehicleList.add(aVehicle);
        });

        String sqlGPS = "insert into t_gps(gprscode, lng, lat) values(?,? ,?) ";
        String sqlVa = "insert into t_vehicle(gprscode, plate_no) values(?,?)";

        jdbcTemplate2.batchUpdate(sqlGPS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AGps aGps = gpsList.get(i);
                ps.setString(1, aGps.getGprscode());
                ps.setDouble(2, aGps.getLat());
                ps.setDouble(3, aGps.getLng());
            }

            @Override
            public int getBatchSize() {
                return gpsList.size();
            }
        });

        jdbcTemplate2.batchUpdate(sqlVa, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AVehicle aVehicle = vehicleList.get(i);
                ps.setString(1, aVehicle.getGprscode());
                ps.setString(2, aVehicle.getPlateNo());
            }
            @Override
            public int getBatchSize() {
                return vehicleList.size();
            }
        });
        System.out.println("同步成功： 数据量：GPS " + gpsList.size() + "; Ve " + vehicleList.size());
    }


}
