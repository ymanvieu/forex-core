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
package fr.ymanvieu.forex.core.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.ymanvieu.forex.core.model.entity.rate.RateEntity;
import fr.ymanvieu.forex.core.provider.AProvider;
import fr.ymanvieu.forex.core.provider.impl.YahooStock;

@Component
public class Stock {

	private final YahooStock provider;

	@Autowired
	public Stock(YahooStock provider) {
		this.provider = provider;
	}

	public AProvider getProvider() {
		return provider;
	}

	public String getCurrency(String code) throws IOException {
		return provider.getCurrency(code);
	}

	public RateEntity getLatestRate(String code, String targetCurrency) throws IOException {
		return provider.getLatestRate(code, targetCurrency);
	}

	public List<RateEntity> getHistoricalRates(String code, String targetCurrency) throws IOException {
		return provider.getHistoricalRates(code, targetCurrency);
	}
}