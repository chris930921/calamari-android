package com.resourcelibrary.model.network.api.ceph.object;

import com.resourcelibrary.model.logic.PortableJsonArray;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by User on 4/22/2015.
 */
public class ClusterV2ServerListData extends PortableJsonArray {
    public ClusterV2ServerListData(String Json) throws JSONException {
        super(Json);
    }

    public ArrayList<ClusterV2ServerData> getList() throws JSONException {
        ArrayList<ClusterV2ServerData> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            String singleData = json.getJSONObject(i).toString();
            list.add(new ClusterV2ServerData(singleData));
        }
        return list;
    }

    public ArrayList<ClusterV2ServerData> getOsdServers() throws JSONException {
        ArrayList<ClusterV2ServerData> result = new ArrayList<>();
        ArrayList<ClusterV2ServerData> servers = getList();
        for (int i = 0; i < servers.size(); i++) {
            ClusterV2ServerData server = servers.get(i);
            ArrayList<ClusterV2ServerServicesData> services = server.getOsdServices();
            if (services.size() != 0) {
                result.add(server);
            }
        }

        Collections.sort(result, new Comparator<ClusterV2ServerData>() {
            public int compare(ClusterV2ServerData one, ClusterV2ServerData other) {
                return one.getNumberBehindHostName() - other.getNumberBehindHostName();
            }
        });
        return result;
    }

    public ArrayList<ClusterV2ServerData> getMonServers() throws JSONException {
        ArrayList<ClusterV2ServerData> result = new ArrayList<>();
        ArrayList<ClusterV2ServerData> servers = getList();
        for (int i = 0; i < servers.size(); i++) {
            ClusterV2ServerData server = servers.get(i);
            ArrayList<ClusterV2ServerServicesData> services = server.getMonServices();
            if (services.size() != 0) {
                result.add(server);
            }
        }
        return result;
    }
}
