#!/usr/bin/env python
# coding: utf-8

# The map_html() function takes latitude and longitude of traveller, a subset of vehicles recommended for the passenger, preferred fuel type of passenger and passenger name as inputs. The function uses folium module to build a map which plots user location in blue marker point and vehicle locations in red marker points. The output ie, the map is stored as a HTML file, which is later used in the dashboard in an iframe. In order to ensure personalization, the HTML file is saved by appending the preferred fuel type to the filename.

# In[1]:


# Importing libraries
import folium
from folium.plugins import MarkerCluster

# In[2]:


def map_html(lat,lng,gas_veh_subset,fuel_type,p_name,dist,ex_veh_set):
    m = folium.Map(location=[lat, lng], zoom_start=15)

    
    marker_cluster = MarkerCluster().add_to(m)
    
    for _, row in ex_veh_set.iterrows():
        if (row["vehicle_type"] == 'moped'): 
            folium.Marker(location=[row["lat"], row["lon"]], 
                      popup=row["vehicle_type"],
                      icon= folium.Icon(icon="bicycle",prefix="fa",color="red", icon_color='lightgoldenrodyellow')).add_to(m)
        if (row["vehicle_type"] == 'motorcycle'): 
            folium.Marker(location=[row["lat"], row["lon"]], 
                      popup=row["vehicle_type"],
                      icon= folium.Icon(icon="motorcycle",prefix="fa",color="red", icon_color='lightcoral')).add_to(m)
        if (row["vehicle_type"].startswith('passenger')): 
            folium.Marker(location=[row["lat"], row["lon"]], 
                      popup="passenger carrier",
                      icon= folium.Icon(icon="bus",prefix="fa",color="red", icon_color='lightblue')).add_to(m)

            
    
    for _, row in gas_veh_subset.iterrows():
        if (row["vehicle_type"] == 'taxi'): 
            folium.Marker(location=[row["lat"], row["lon"]],
                      popup=row["driver_name"]+"---"+row["vehicle_type"]+"---"+row["fuel_type"], 
                      icon= folium.Icon(icon="taxi",prefix="fa",color="lightgreen", icon_color='darkblue')).add_to(marker_cluster)
        if (row["vehicle_type"] == 'uber'): 
            folium.Marker(location=[row["lat"], row["lon"]],
                      popup=row["driver_name"]+"---"+row["vehicle_type"]+"---"+row["fuel_type"], 
                      icon= folium.Icon(icon="car",prefix="fa",color="darkgreen", icon_color='lightblue')).add_to(marker_cluster)   
    



    folium.Circle (location=[lat, lng], radius=dist, fill_color='blue').add_to(m)
    folium.Marker(location=[lat, lng],
                  popup=str(p_name),
                      icon= folium.Icon(icon="glyphicon-user",color="blue", icon_color='lightblue')).add_to(m)

    m.save('maps/avail_'+fuel_type+'_veh.html')   
    return
