/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.hint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.dao.ObjectWithID;

/**
 *
 * @author y50u
 */
public class HintClass implements Hint, ObjectWithID {

    private Integer id;

    private String title;
    private String comment;
    private String url;
    private Date read_time;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HintClass(Integer id, String title, String comment, String url) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.url = url;
        this.read_time=null;
    }
    public HintClass(Integer id, String title, String comment, String url, Date read_time) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.url = url;
        this.read_time=read_time;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getID() {
        return id;
    }
    @Override
    public Date getTimestamp() {
        return this.read_time;
    }
    
    @Override
    public String printAll() {
        String ret="\tOtsikko: " + title + "\n\tKommentti: " + comment
                +"\n\tURL: " + url;
        if(this.read_time!=null) ret+="\n\tluettu: " + formatDate();
        
        return ret;
    }

    @Override
    public String toString() {
        return "\tID: "+id+ "\n\tOtsikko: " + title + "\n\tKommentti: " + comment
                +"\n\tURL: " + url;
    }
    public String formatDate() {
        TimeZone tz = TimeZone.getDefault();
        long off=tz.getOffset(new Date().getTime());
        read_time.setTime(read_time.getTime()+off);
        
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(read_time);
      
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String ret=sdf.format(calendar.getTime());
        return ret;
    }
}
