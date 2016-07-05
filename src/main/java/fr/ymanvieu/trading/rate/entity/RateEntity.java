/**
 * Copyright (C) 2014 Yoann Manvieu
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
package fr.ymanvieu.trading.rate.entity;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.MoreObjects;

import fr.ymanvieu.trading.symbol.entity.SymbolEntity;

@JsonInclude(Include.NON_NULL)
@MappedSuperclass
public class RateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "fromcur", referencedColumnName = "code")
	private SymbolEntity fromcur;

	@ManyToOne
	@JoinColumn(name = "tocur", referencedColumnName = "code")
	private SymbolEntity tocur;

	@Column(precision = 20, scale = 10, nullable = false)
	private BigDecimal value;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

	public RateEntity() {
	}

	public RateEntity(String from, String to, BigDecimal rate, Date date) {
		this.fromcur = new SymbolEntity(requireNonNull(from, "from is null"));
		this.tocur = new SymbolEntity(requireNonNull(to, "to is null"));
		this.value = requireNonNull(rate, "rate is null");
		this.date = requireNonNull(date, "date is null");
	}

	public RateEntity(SymbolEntity from, SymbolEntity to, BigDecimal rate, Date date) {
		this.fromcur = requireNonNull(from, "from is null");
		this.tocur = requireNonNull(to, "to is null");
		this.value = requireNonNull(rate, "rate is null");
		this.date = requireNonNull(date, "date is null");
	}

	public SymbolEntity getFromcur() {
		return fromcur;
	}

	public SymbolEntity getTocur() {
		return tocur;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, fromcur.getCode(), tocur.getCode(), value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || !(obj instanceof RateEntity))
			return false;

		RateEntity other = (RateEntity) obj;

		return date.compareTo(other.date) == 0 //
				&& Objects.equals(fromcur.getCode(), other.fromcur.getCode()) //
				&& Objects.equals(tocur.getCode(), other.tocur.getCode()) //
				&& value.compareTo(other.value) == 0;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this) //
				.add("date", date) //
				.add("from", fromcur.getCode()) //
				.add("to", tocur.getCode()) //
				.add("value", value).toString();
	}
}