package insights;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.discovery.v1.model.CreateCollectionOptions.Language;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;


public class Insights_lib {
	PersonalityInsights service;
	IamOptions iamOptions;
	
	public Insights_lib()
	{	
		service = new PersonalityInsights("2016-10-19");
		iamOptions = new IamOptions.Builder()
		  .apiKey("1618101")
		  .build();
		service.setIamCredentials(iamOptions);
		
	}
	
	public Profile getText(String text)
	{	
		ProfileOptions options = new ProfileOptions.Builder()
			//.contentLanguage(Language.JA)
			//.acceptLanguage(Language.JA) 
			.text(text)
			.build();

		Profile profile = service.profile(options).execute();
		
		return profile;
	}
	
	public void getInsights(Profile profile)
	{
		MySQL my_sql = new MySQL(); 
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = null;
		try {
			node = mapper.readTree(String.valueOf(profile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double personality[] = new double[100];
		int count = 0;
		for( int i = 0 ; i < 5 ; i ++ )  
		{
			personality[count] = node.get("personality").get(i).get("percentile").asDouble();
			count++;
		}
		for( int i = 0 ; i < 5 ; i ++ )  
		{
			for( int j = 0 ; j < 6 ; j ++ ) 
			{
			personality[count] = node.get("personality").get(i).get("children").get(j).get("percentile").asDouble();
			count++;
			}
		}
		String text = node.get("word_count_message").toString();
		
		my_sql.updateImage(personality,text);
		System.out.println(profile);
		
		
	}
}
