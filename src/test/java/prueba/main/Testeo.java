package prueba.main;

import com.google.gson.*;
import datos.EstacionRecarga;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Map;

public class Testeo {

	@Test
	public void testCountRevisions(){
		JsonParser parser = new JsonParser();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("icaen.json");
		Reader reader = new InputStreamReader(inputStream);
		JsonElement rootElement = parser.parse(reader);
		Assert.assertNotNull(rootElement);

		JsonObject rootObject = rootElement.getAsJsonObject();
		JsonArray array = null;
		for(Map.Entry<String, JsonElement> entry:rootObject.entrySet()){
			JsonObject entryObject = entry.getValue().getAsJsonObject();
			array = entryObject.getAsJsonArray("revisions");
			//Assert.assertNotNull(rootObject);
		}
		Assert.assertEquals(4, array.size());

	}

	@Test
	public void testGson() throws FileNotFoundException {
		EstacionRecarga nodo;
		FileReader reader = new FileReader("src/test/resources/icaen.json");
		String s = reader.toString();

		nodo = new Gson().fromJson("icaen.j", EstacionRecarga.class);
		System.out.println(nodo.latitud);
	}

}
