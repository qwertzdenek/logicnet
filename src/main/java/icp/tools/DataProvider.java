package icp.tools;

import java.io.IOException;


public interface DataProvider {

	/**
	 * cte vety az do doby co vrati null
	 * @return
	 * @throws IOException
	 */
	public String[] next() throws IOException;

	/**
	 * zavrit na konci
	 * @throws IOException
	 */
	public void close() throws IOException;

}
