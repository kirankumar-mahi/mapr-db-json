package retail.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ojai.Document;
import org.ojai.DocumentStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mapr.db.MapRDB;
import com.mapr.db.Table;

import retail.data.User;
import retail.util.MapRJsonDBHelper;

@Component
public class UserDAO {

	@Autowired
	private Environment env;
	@Autowired
	private MapRJsonDBHelper dbHelper;

	private Table userTable;

	public List<User> list() {
		try {
			userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			ArrayList<User> userList = new ArrayList<User>();
			DocumentStream rs = userTable.find();
			Iterator<Document> itrs = rs.iterator();
			while (itrs.hasNext()) {
				userList.add(itrs.next().toJavaBean(User.class));
			}
			rs.close();
			return userList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public User get(String id) {
		try {
			userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			User user = userTable.findById(id).toJavaBean(User.class);
			return user;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


	}

	public String create(User user) {
		try {
			userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			Document document = MapRDB.newDocument(user);
			userTable.insertOrReplace(document);
			userTable.flush();
			return "user created successfully" ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}

	}

	public User delete(String userId) {
		try {
			userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			Document userDocument = userTable.findById(userId);
			userTable.delete(userDocument);
			userTable.flush();
			return userDocument.toJavaBean(User.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String update(User user) {
		try {
			userTable = dbHelper.getTable(env.getProperty("user.table.name"));
			Document userDocument = userTable.findById(user.getId());
			if(userDocument != null){
				Document document = MapRDB.newDocument(user);
				userTable.insertOrReplace(document);
				userTable.flush();
				return "Update Success" ;			
			}else{
				return  null;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
	}

}
