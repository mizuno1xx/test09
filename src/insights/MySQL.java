package insights;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MySQL 
{
	String driver;// JDBCドライバの登録
    String server, dbname, url, user, password;// データベースの指定
    Connection con;
    Statement stmt;
    Map<String, Object> lng = new HashMap<>();

	public MySQL() 
	{
		this.driver		= "org.gjt.mm.mysql.Driver";
        this.server 	= "sangi2018.sist.ac.jp";
        this.dbname 	= "1618101";
        this.url		= "jdbc:mysql://" + server + "/" + dbname + "?useUnicode=true&characterEncoding=UTF-8";
        this.user		= "1618101";
        this.password	= "sist1618101";
        
        
        try 
        {
            this.con = DriverManager.getConnection(url, user, password);
            this.stmt = con.createStatement ();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        try
        {
            Class.forName (driver);
        } 
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
	}
		
	public ResultSet getID() 
	{
		ResultSet rs = null;
		String sql = "SELECT * FROM  `images` WHERE  `age_min` = -1";
		try 
		{
			rs = stmt.executeQuery (sql);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void updateImage( double personality[] , String text ) 
	{
		StringBuffer buf = new StringBuffer();
		
		String sql_;
		
		sql_ = "INSERT INTO  `screens`(`openness`,`conscientiousness`,`extraversion`,`agreeableness`,`neuroticism`,`adventurousness`,`artistic`,`emotionality`,`imagination`,`intellect`,`challenging`,`striving`,`cautiousness`,`dutifulness`,`orderliness`,`discipline`,`efficacy`,`activity`,`assertiveness`,`cheerfulness`,`seeking`,`outgoing`,`gregariousness`,`altruism`,`cooperation`,`modesty`,`uncompromising`,`sympathy`,`trust`,`fiery`,`worry`,`melancholy`,`immoderation`,`consciousness`,`susceptible`,`text`)";
		sql_ += "VALUES (";
		sql_ += personality[0];
		for( int i = 1 ; i < 35 ; i ++ )
		{
			sql_ += " , " + personality[i];
		}
		sql_ += " , " + text;
		sql_ += " ); ";
		
		//System.out.println(sql_);
		
		buf.append(sql_);
		String sql = buf.toString();
		try 
		{
			stmt.execute (sql);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}