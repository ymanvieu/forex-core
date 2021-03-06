/**
 * Copyright (C) 2016 Yoann Manvieu
 * 
 * This software is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ymanvieu.trading.common.provider.rate;

import java.io.IOException;
import java.util.List;

import fr.ymanvieu.trading.common.provider.Quote;

public interface LatestRateProvider {

	/**
	 * Returns the latest possible rates value (implementation dependent).
	 * 
	 * @return List of latest rates
	 * @throws IOException
	 */
	List<Quote> getRates() throws IOException;

	/**
	 * @param code  
	 * @throws IOException 
	 */
	default Quote getLatestRate(String code) throws IOException {
		throw new RuntimeException("not implemented");
	}
}
