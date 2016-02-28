package com.game.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.game.common.basics.ApplicationContextLoader;
import com.game.platform.domain.PlatformZone;
import com.game.platform.persistence.dao.PlatformZoneDao;

@Service
public class LocationListener implements ApplicationListener<ContextRefreshedEvent> {
	public static List<PlatformZone> ZONE_PROVINCE = new ArrayList<PlatformZone>();
	public static Map<Integer, List<PlatformZone>> ZONE_CITY_MAP = new HashMap<Integer, List<PlatformZone>>();
	public static Map<Integer, List<PlatformZone>> ZONE_AREA_MAP = new HashMap<Integer, List<PlatformZone>>();
	private static PlatformZoneDao dao;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
    	dao = (PlatformZoneDao) ApplicationContextLoader.getBean("platformZoneDao");
    	List<PlatformZone> list = dao.selectZoneProvince();
    	List<PlatformZone> finalList = new ArrayList<PlatformZone>();
    	for(PlatformZone zone : list){
    		zone.setZoneNames("");
    		zone.setParentId(0);
    		finalList.add(zone);
    	}
    	ZONE_PROVINCE = finalList;
    }
    
    public static List<PlatformZone> getZoneCity(Integer provineCode){
    	if(provineCode == null || provineCode == -1 || provineCode == 0) return null;
    	List<PlatformZone> list = ZONE_CITY_MAP.get(provineCode);
    	if(list == null){
    		list = dao.selectZoneCity(provineCode);
    		if(list != null && list.size() > 0) {
    			PlatformZone zone = list.get(0);
    			List<PlatformZone> finalList = doWhith(zone.getZoneNames());
    			ZONE_CITY_MAP.put(provineCode, finalList);
    			return finalList;
    		}
    	}
    	return list;
    }
    
    public static List<PlatformZone> getZoneArea(Integer cityCode){
    	if(cityCode == null || cityCode == -1 || cityCode == 0) return null;
    	List<PlatformZone> list = ZONE_AREA_MAP.get(cityCode);
    	if(list == null){
    		list = dao.selectZoneArea(cityCode);
    		if(list != null && list.size() > 0) {
    			PlatformZone zone = list.get(0);
    			List<PlatformZone> finalList = doWhith(zone.getZoneNames());
    			ZONE_AREA_MAP.put(cityCode, finalList);
    			return finalList;
    		}
    	}
    	return list;
    }
    
    private static List<PlatformZone> doWhith(String zoneNames){
    	String[] zones = zoneNames.split(",");
    	List<PlatformZone> list = new ArrayList<PlatformZone>();
    	for(String zone : zones){
    		String[] temp = zone.split("\\|");
    		PlatformZone pz = new PlatformZone();
    		pz.setCode(Integer.valueOf(temp[0]));
    		pz.setName(temp[1]);
    		pz.setZoneNames("");
    		pz.setId(0);
    		pz.setParentId(0);
    		list.add(pz);
    	}
    	return list;
    }
}