package retail.util;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.mapr.db.MapRDB;
import com.mapr.db.Table;

@Component
public class MapRJsonDBHelper {
	
	  private Table table;
	
	  /**
	   * Get the table, create it if not present
	   *
	   * @throws IOException
	   */
	  public Table getTable(String tableName) throws IOException {
	    Table table;

	    if (!MapRDB.tableExists(tableName)) {
	      table = MapRDB.createTable(tableName); // Create the table if not already present
	    } else {
	      table = MapRDB.getTable(tableName); // get the table
	    }
	    return table;
	  }

	  public void deleteTable(String tableName) throws IOException {
	    if (MapRDB.tableExists(tableName)) {
	      MapRDB.deleteTable(tableName);
	    }

	  }

	  
}
