package de.unikoblenz.west.koldfish.koldfish_test;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import de.unikoblenz.west.koldfish.dictionary.Dictionary;
import junit.framework.TestCase;

public class crawlerTest extends TestCase {

	@SuppressWarnings("resource")
	public void testcrawler() throws Exception {
		Dictionary dictionary = new Dictionary();
		List<Long> expected = Arrays.asList(-588153461L, -1209624855L);
		List<Long> actual = null;
		Scanner s = new Scanner("/home/rahul/Desktop/seedlist.txt");

		while (s.hasNextLine()) {

			actual = dictionary.convertIris(Arrays.asList(s.nextLine()));
		}
		for (int i = 0; i < expected.size(); i++)
			if (i < actual.size())
				assertEquals(expected.get(i), actual.get(i));

	}

}
