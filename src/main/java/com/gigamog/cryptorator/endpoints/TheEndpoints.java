package com.gigamog.cryptorator.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gigamog.cryptorator.Cryptorator;
import com.gigamog.cryptorator.Propertizer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;

@Path("/")
public class TheEndpoints {
	
	
	@GET
	@Path("cr/{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response encrypt(@PathParam("content") String content) {
		EncryptedContent ec = new EncryptedContent(content);
		return Response.status(200).entity(ec.encrypt()).build();
    }
	
	@GET
	@Path("ecr/{content}/{salt}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response decrypt(@PathParam("content") String content, @PathParam("salt") String salt) {		
		EncryptedContent ec = new EncryptedContent(content, salt);
		return Response.status(200).entity(ec.decrypt()).build();
    }
	
	@POST
	@Path("cr")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response encryptPost(final String input) {
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonObject element = jsonParser.parse(input).getAsJsonObject();
		EncryptedContent ec = new EncryptedContent(element.get("content").getAsString());
		return Response.status(200).entity(ec.encrypt()).build();
    }
	
	
	@POST
	@Path("ecr")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response decryptPost(final String input) {
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonObject element = jsonParser.parse(input).getAsJsonObject();
		EncryptedContent ec = new EncryptedContent(element.get("content").getAsString(), element.get("id").getAsString());
		return Response.status(200).entity(ec.decrypt()).build();
    }
	
	
	class EncryptedContent{
		
		transient Cryptorator cr = new Cryptorator();
		@Expose
		String content;
		@Expose
		String id = cr.generateSalt(Propertizer.getSaltLength());
		
		public EncryptedContent(String content){
			this.content = content;
		}
		
		public EncryptedContent(String content, String id){
			this.content = content;
			this.id = id;
		}
		
		String encrypt(){
			
			Gson gson = new Gson();
			content = cr.encryptContent(content, Propertizer.getEncryptionPassword(), id);
			return gson.toJson(this);	
		}
		
		String decrypt(){
			Gson gson = new Gson();
			content = cr.decryptContent(content, Propertizer.getEncryptionPassword(), id);
			return gson.toJson(this);
		}
	}
	

}
