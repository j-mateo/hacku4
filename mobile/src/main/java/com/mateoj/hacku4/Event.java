package com.mateoj.hacku4;

import org.joda.time.DateTime;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Divya Bathey on 2/4/2016.
 */
public class Event {

    private String name;
    private String location;
    private String description;
    private ArrayList<String> tags;
    private DateTime start;
    private DateTime end;

    // full constructor:
    // new Event("Hackathon", "McGlothlin", ["fun","CS"], "A fun event for CS students.", Start, End);
    Event (String text1, String text2, ArrayList<String> list1, String text3, DateTime date1, DateTime date2){
        name = text1;
        location = text2;
        tags = list1;
        description = text3;
        start = date1;
        end = date2;
    }

    public String getName() { if(name == null) { return "Messed up."; } return name; }
    public void setName(String text1) { name = text1; }

    public String getLocation() { return location; }
    public void setLocation(String text2) { location = text2; }

    public String getDescription() { return description; }
    public void setDescription(String text3) { description = text3; }

    public DateTime getStart() { return start; }
    public DateTime getEnd() { return end; }
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