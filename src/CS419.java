
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ListIterator;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Group;
import com.restfb.types.Message;
import com.restfb.types.User;

public class CS419 
{
	//enter your access code here
	static String accessToken = "insert access token here";
	static Charset utf8 = StandardCharsets.UTF_8;
	
	public static void main(String[] args) throws IOException
	{	
		//use a userID
		listUsername("653675571424696");
		listUsername("859464984119647");
		listUsername("100014093474656");
		
		//use a groupID
		listGroupName("726855587423085");
		listGroupDescription("726855587423085");
		listGroupCoverPhotoURL("726855587423085");
			listGroupInfoToFile("726855587423085");
		listMembers("726855587423085");
			listMembersToFile("726855587423085");
		
		//use a messageID
		listLikes("984620651646576");
		listLikes("996954357079872");
			listLikesToFile("984620651646576");

	}
	//returns a User object for use in other methods
	public static User fetchUser(String userID) 
	{
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);	
		User user = facebookClient.fetchObject(userID, User.class, Parameter.with("fields", "id,name"));
		return user;
	}
	//returns a Group object for use in other methods
	public static Group fetchGroup(String groupID)
	{
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);	
		Group group = facebookClient.fetchObject(groupID, Group.class, Parameter.with("fields", "id,name,description, cover"));
		return group;
	}
	//returns a Connection of type <Group> for use in other methods (limited to 50 entries/members)
	public static Connection<Group> fetchConnection(String groupID)
	{
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);	
		Connection<Group> group = facebookClient.fetchConnection(groupID, Group.class, Parameter.with("fields", "id,name,likes"), Parameter.with("limit", 50));
		return group;
	}
	//returns a Connection of type <Message> for use in other methods (limited to 50 entries/likes)
	public static Connection<Message> fetchMessageConnection(String messageID)
	{
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);	
		Connection<Message> message = facebookClient.fetchConnection(messageID, Message.class, Parameter.with("fields", "id,name,likes"), Parameter.with("limit", 50));
		return message;
	}
	//prints a username for the given userID
	public static void listUsername(String userID)
	{
		System.out.println("User: " + fetchUser(userID).getName());
	}
	//prints a group name for the given groupID
	public static void listGroupName(String groupID)
	{
		System.out.println("Group Name: " + fetchGroup(groupID).getName());
	}
	//prints a group description for the given groupID
	public static void listGroupDescription(String groupID)
	{
		System.out.println("Group Description: " + fetchGroup(groupID).getDescription());
	}
	//prints a URL to the groups cover photo given the groupID
	public static void listGroupCoverPhotoURL(String groupID)
	{
		System.out.println("Group Cover Photo URL: " + fetchGroup(groupID).getCover().getSource());
	}
	//prints group name, description, cover photo URL to a file located in the workspace
	public static void listGroupInfoToFile(String groupID) throws IOException
	{
		BufferedWriter output = null;
        try 
        {
            File file = new File("file3.txt");
            output = new BufferedWriter(new FileWriter(file));
            
            Group group = fetchGroup(groupID);
    			output.write("Group Name: " + group.getName() + System.lineSeparator() + "Group Description: " + group.getDescription() + System.lineSeparator() + "Group Cover Photo URL: " + group.getCover().getSource());
        } 
        catch ( IOException e ) 
        {
            e.printStackTrace();
        } 
        finally 
        {
          if ( output != null ) 
          {
            output.close();
          }
        }
	}
	//prints a list of the members of the group for a given groupID (is limited to 50 members in the fetchConnection(String groupID) method)
	public static void listMembers(String groupID)
	{
		System.out.println("~~~~~~~~Group: ~~~~~~~~~~~~~~~~~~~~~~~~");
		Connection<Group> group = fetchConnection(groupID + "/members");
		ListIterator<Group> it = group.getData().listIterator();
		while (it.hasNext())
		{
			System.out.println("	Member: " + it.next().getName());
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	//prints members of a group to a file located in the workspace
	public static void listMembersToFile(String groupID) throws IOException
	{
		BufferedWriter output = null;
        try 
        {
            File file = new File("file1.txt");
            output = new BufferedWriter(new FileWriter(file));
            
            Connection<Group> group = fetchConnection(groupID + "/members");
    		ListIterator<Group> it = group.getData().listIterator();
    		output.write("Members: " + System.lineSeparator());
    		while (it.hasNext())
    		{
    			output.write((it.next().getName() + System.lineSeparator()));
    		}	
        } 
        catch ( IOException e ) 
        {
            e.printStackTrace();
        } 
        finally 
        {
          if ( output != null ) 
          {
            output.close();
          }
        }
		
	}
	
	//prints a list of people (usernames) who liked a message given a messageID
	public static void listLikes(String messageID)
	{
		System.out.println("~~~~~~~~Likes Message: ~~~~~~~~~~~~~~~~~~~~~~~~");
		Connection<Group> group = fetchConnection(messageID + "/likes");
		ListIterator<Group> it = group.getData().listIterator();
		while (it.hasNext())
		{
			System.out.println("	Person: " + it.next().getName());
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	//prints likers of a message to a file located in the workspace
		public static void listLikesToFile(String messageID) throws IOException
		{
			BufferedWriter output = null;
	        try 
	        {
	            File file = new File("file2.txt");
	            output = new BufferedWriter(new FileWriter(file));
	            
	            Connection<Group> group = fetchConnection(messageID + "/likes");
	    		ListIterator<Group> it = group.getData().listIterator();
	    		output.write("Persons who Liked: " + System.lineSeparator());
	    		while (it.hasNext())
	    		{
	    			output.write((it.next().getName() + System.lineSeparator()));
	    		}	
	        } 
	        catch ( IOException e ) 
	        {
	            e.printStackTrace();
	        } 
	        finally 
	        {
	          if ( output != null ) 
	          {
	            output.close();
	          }
	        }
			
		}
}
