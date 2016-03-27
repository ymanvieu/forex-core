/**
 * Copyright (C) 2015 Yoann Manvieu
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
package fr.ymanvieu.forex.core.model.repositories;

import static fr.ymanvieu.forex.core.Utils.symbol;
import static fr.ymanvieu.forex.core.util.CurrencyUtils.GBP;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.ymanvieu.forex.core.ForexApplication;
import fr.ymanvieu.forex.core.model.entity.symbol.SymbolEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ForexApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SymbolRepositoryTest {

	@Autowired
	private SymbolRepository repo;

	@Sql("/sql/insert_data.sql")
	@Test
	@Transactional
	public void testDeleteByCode() {
		assertThat(repo.count()).isEqualTo(8);
		// when
		int result = repo.deleteByCode("UBI.PA");

		// then
		assertThat(result).isEqualTo(1);
		assertThat(repo.count()).isEqualTo(7);
	}

	@Sql({ "/sql/insert_data.sql", "/sql/insert_eur_gbp.sql" })
	@Test
	public void testFindAllByCurrencyCode() {
		// when
		List<SymbolEntity> result = repo.findAllByCurrencyCode(GBP);

		// then
		assertThat(result).hasSize(1);
		assertThat(result).contains(symbol("RR.L", "Rolls Royce Holdings plc", null, symbol(GBP, "British Pound Steerling", "gbp", null)));
	}
}
