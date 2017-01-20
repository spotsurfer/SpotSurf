package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "spotsurf-mobilehub-353578097-Spots")

public class SpotsDO {
    private String _userId;
    private String _price;
    private Set<String> _dailyWeeklyMonthly;
    private Set<String> _drivewayGarage;
    private String _endDate;
    private Double _garageCode;
    private String _size;
    private String _startDate;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "Price")
    @DynamoDBAttribute(attributeName = "Price")
    public String getPrice() {
        return _price;
    }

    public void setPrice(final String _price) {
        this._price = _price;
    }
    @DynamoDBAttribute(attributeName = "Daily/Weekly/Monthly")
    public Set<String> getDailyWeeklyMonthly() {
        return _dailyWeeklyMonthly;
    }

    public void setDailyWeeklyMonthly(final Set<String> _dailyWeeklyMonthly) {
        this._dailyWeeklyMonthly = _dailyWeeklyMonthly;
    }
    @DynamoDBAttribute(attributeName = "Driveway/Garage")
    public Set<String> getDrivewayGarage() {
        return _drivewayGarage;
    }

    public void setDrivewayGarage(final Set<String> _drivewayGarage) {
        this._drivewayGarage = _drivewayGarage;
    }
    @DynamoDBAttribute(attributeName = "End Date")
    public String getEndDate() {
        return _endDate;
    }

    public void setEndDate(final String _endDate) {
        this._endDate = _endDate;
    }
    @DynamoDBAttribute(attributeName = "Garage Code")
    public Double getGarageCode() {
        return _garageCode;
    }

    public void setGarageCode(final Double _garageCode) {
        this._garageCode = _garageCode;
    }
    @DynamoDBAttribute(attributeName = "Size")
    public String getSize() {
        return _size;
    }

    public void setSize(final String _size) {
        this._size = _size;
    }
    @DynamoDBAttribute(attributeName = "Start Date")
    public String getStartDate() {
        return _startDate;
    }

    public void setStartDate(final String _startDate) {
        this._startDate = _startDate;
    }

}
