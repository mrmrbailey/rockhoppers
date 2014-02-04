package uk.co.rockhoppersuk.byRef;

import static org.junit.Assert.*;

import org.junit.Test;

public class CatalogTest {

	@Test
	public final void testCatalog() {
		Catalog cat = new Catalog();
		Thing aThing = cat.getThing("a");
		
		assertEquals(1,aThing.getValue());
		aThing.setValue(10);

		Thing anotherThing = cat.getThing("a");
		assertEquals(10,anotherThing.getValue());
	}

}
