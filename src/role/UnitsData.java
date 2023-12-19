package role;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitsData {

	private Map<String, List<Record>> datacontext = new HashMap<>();
	private static UnitsData instance = null;

	public static UnitsData getInstance(String path) {
		if (instance == null)
			instance = new UnitsData(path);
		return instance;
	}

	/**
	 * units.dat在在classes中的位置
	 * 
	 * @param path units.dat
	 */
	private UnitsData(String path) {
		super();
//		path = UnitsData.class.getClassLoader().getResource(path).getPath();

		// String[] strArr = new String[] {"1","2","3"};
		// String str = strArr.toString();
		// System.out.println("Java String array to String = "+str);

		// path = (String)path.split("!").toString();
		// path =(String)"./units.dat";

//		System.out.println(path);
		try {
			FileReader file = new FileReader(path);
			BufferedReader br = new BufferedReader(file);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] its = line.split(",");
				Record record = new Record(its[0], Integer.parseInt(its[1]), Integer.parseInt(its[2]));
				List<Record> list = datacontext.get(its[0]);
				if (list == null)
					list = new ArrayList<>();
				list.add(record);
				datacontext.put(its[0], list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取 " + path + " 文件出错." + e.getMessage());
		}
	}

	// public static void main(String[] args) {
	// UnitsData d = UnitsData.getInstance("units.dat");
	// }

	public List<Record> getRecords(String rolekey) {
		return datacontext.get(rolekey);
	}

	public static class Record {
		private String name;
		private int posx;
		private int posy;

		public Record(String name, int posx, int posy) {
			super();
			this.name = name;
			this.posx = posx;
			this.posy = posy;
		}

		public String getName() {
			return name;
		}

		public int getPosx() {
			return posx;
		}

		public int getPosy() {
			return posy;
		}

	}
}
