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
package fr.ymanvieu.forex.core.model.entity.rate;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.ymanvieu.forex.core.model.entity.symbol.SymbolEntity;

@Entity
@Table(name = "rates")
@IdClass(HistoricalRateId.class)
public class HistoricalRate extends RateEntity {

	public HistoricalRate(RateEntity re) {
		super(re.getFromcur(), re.getTocur(), re.getValue(), re.getDate());
	}

	public HistoricalRate() {
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "fromcur", referencedColumnName = "code")
	@Override
	public SymbolEntity getFromcur() {
		return super.getFromcur();
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "tocur", referencedColumnName = "code")
	@Override
	public SymbolEntity getTocur() {
		return super.getTocur();
	}

	@Id
	@Temporal(TemporalType.TIMESTAMP)
	@Override
	public Date getDate() {
		return super.getDate();
	}
}
