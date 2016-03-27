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

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import fr.ymanvieu.forex.core.model.entity.rate.HistoricalRate;

public interface HistoricalRateRepository extends JpaRepository<HistoricalRate, Long>, QueryDslPredicateExecutor<HistoricalRate> {

	@Query("select date,value from #{#entityName} where fromcur.code=:fromcur and tocur.code=:tocur and ((:start is null or :end is null) or (date between :start and :end)) order by date")
	List<Object[]> findDateValues(@Param("fromcur") String fromcur, @Param("tocur") String tocur, @Param("start") Date startDate,
			@Param("end") Date endDate);

	@Query(nativeQuery = true, value = "SELECT min(date),avg(value) FROM rates where fromcur=:fromcur and tocur=:tocur and (date between :start and :end) group by (unix_timestamp(date) DIV 3600) order by date")
	List<Object[]> findHourlyValues(@Param("fromcur") String fromcur, @Param("tocur") String tocur, @Param("start") Date startDate,
			@Param("end") Date endDate);

	@Query("SELECT min(date),avg(value) FROM #{#entityName} where fromcur.code=:fromcur and tocur.code=:tocur and ((:start is null or :end is null) or (date between :start and :end)) group by TO_DAYS(date) order by date")
	List<Object[]> findDailyValues(@Param("fromcur") String fromcur, @Param("tocur") String tocur, @Param("start") Date startDate,
			@Param("end") Date endDate);

	@Query(nativeQuery = true, value = "SELECT min(date),avg(value) FROM rates where fromcur=:fromcur and tocur=:tocur and ((:start is null or :end is null) or (date between :start and :end)) group by YEARWEEK(date, 1) order by date")
	List<Object[]> findWeeklyValues(@Param("fromcur") String fromcur, @Param("tocur") String tocur, @Param("start") Date startDate,
			@Param("end") Date endDate);

	@Modifying
	@Query("delete FROM #{#entityName} where fromcur.code=:code or tocur.code=:code")
	int deleteByFromcurCodeOrTocurCode(@Param("code") String code);

	HistoricalRate findFirstByFromcurCodeAndTocurCode(String fromcur, String tocur, Sort order);
}