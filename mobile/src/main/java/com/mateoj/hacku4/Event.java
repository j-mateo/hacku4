package com.mateoj.hacku4;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.joda.time.DateTime;

import java.util.ArrayList;


/**
 * Created by Divya Bathey on 2/4/2016.
 */
@ParseClassName("Event")
public class Event extends ParseObject{
    private String location;
    private String description;
    private ArrayList<String> tags;
    private DateTime start;
    private DateTime end;

    // full constructor:
    // new Event("Hackathon", "McGlothlin", ["fun","CS"], "A fun event for CS students.", Start, End);
//    Event (String text1, String text2, ArrayList<String> list1, String text3, DateTime date1, DateTime date2){
//        name = text1;
//        location = text2;
//        tags = list1;
//        description = text3;
//        start = date1;
//        end = date2;
//    }

    public String getName() {
        return (getString("Name") == null) ? "" : getString("Name");
    }
    public void setName(String text1) {
        put("Name", text1);
    }

    public Building getLocation() {
        return (Building) getParseObject("Location");
    }

    public void setLocation(Building building) {
        put("Location", building);
    }

    public String getDescription() { return description; }
    public void setDescription(String text3) { description = text3; }

    public DateTime getStart() {
        return getDate("Time") == null ?  new DateTime() : new DateTime(getDate("Time")) ;
    }

    public DateTime getEnd() {
        return getDate("EndTime") == null ?  new DateTime() : new DateTime(getDate("EndTime")) ;
    }

    public void setStart(DateTime date1) { start = date1; }
    public void getEnd(DateTime date2) { end = date2; }

    public ArrayList<String> getTags() { return tags; }
    public void setTags(ArrayList<String> list1) { tags = list1; }
    public void addTag(String newTag) { tags.add(newTag); }
    public void removeTag(String oldTag) {
        if (tags != null) {
            for (int i = 0; i < tags.size(); i++) {
                if (tags.get(i) == oldTag) {
                    tags.remove(i);
                }
            }
        }
    }

}