package retail.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.ojai.Document;
import org.ojai.DocumentStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mapr.db.MapRDB;
import com.mapr.db.Table;
import com.mapr.db.exceptions.TableClosedException;

import retail.data.User;
import retail.util.MapRJsonDBHelper;

@Component
public class UserDAO {

	@Autowired private Environment env;
	@Autowired private MapRJsonDBHelper dbHelper;

	private Table userTable;
	
	@PostConstruct
	public void getTableHandler(){
		try {
			userTable = dbHelper.getTable(env.getProperty("user.table.name"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<User> list() {
			//userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			ArrayList<User> userList = new ArrayList<User>();
			DocumentStream rs = userTable.find();
			Iterator<Document> itrs = rs.iterator();
			while (itrs.hasNext()) {
				userList.add(itrs.next().toJavaBean(User.class));
			}
			rs.close();
			return userList;
	}

	public User get(String id) {
			//userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			User user = userTable.findById(id).toJavaBean(User.class);
			return user;
	}

	public String create(User user) {
			//userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			Document document = MapRDB.newDocument(user);
			userTable.insertOrReplace(document);
			userTable.flush();
			return "user created successfully" ;
	}

	public User delete(String userId) {
			//userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			Document userDocument = userTable.findById(userId);
			userTable.delete(userDocument);
			userTable.flush();
			return userDocument.toJavaBean(User.class);

	}

	public String update(User user) {
			//userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			Document userDocument = userTable.findById(user.getId());
			if(userDocument != null){
				Document document = MapRDB.newDocument(user);
				userTable.insertOrReplace(document);
				userTable.flush();
				return "Update Success" ;			
			}else{
				return  null;
			}
	}

}
