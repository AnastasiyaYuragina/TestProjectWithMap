
package com.anastasiyayuragina.testproject.jsonCountriesClasses;

import java.util.HashMap;
import java.util.Map;

import com.anastasiyayuragina.testproject.ourDataBase.MyDatabase;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "name",
    "longitude",
    "latitude"
})

@Table(database = MyDatabase.class)
public class Country extends BaseModel {

    @PrimaryKey
    @JsonProperty("id")
    private String id;

    @Column
    @JsonProperty("name")
    private String name;

//    @Column
//    @JsonProperty("region")
//    private Region region;

    @Column
    @JsonProperty("longitude")
    private String longitude;

    @Column
    @JsonProperty("latitude")
    private String latitude;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    private String comment;

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

//    /**
//     *
//     * @return
//     *     The region
//     */
//    @JsonProperty("region")
//    public Region getRegion() {
//        return region;
//    }
//
//    /**
//     *
//     * @param region
//     *     The region
//     */
//    @JsonProperty("region")
//    public void setRegion(Region region) {
//        this.region = region;
//    }

    /**
     * 
     * @return
     *     The longitude
     */
    @JsonProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    /**
     * 
     * @param longitude
     *     The longitude
     */
    @JsonProperty("longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 
     * @return
     *     The latitude
     */
    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    /**
     * 
     * @param latitude
     *     The latitude
     */
    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }




}
