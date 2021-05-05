package com.seamfix.maontestapplication.data.model.response.competitions

import com.seamfix.maontestapplication.data.model.common.Filters
import com.seamfix.maontestapplication.data.model.entity.Competition

data class CompetitionsResponse(
	val count: Int? = null,
	val competitions: List<Competition>? = null,
	val filters: Filters? = null
)

data class Winner(
	val crestUrl: String? = null,
	val name: String? = null,
	val tla: String? = null,
	val id: Int? = null,
	val shortName: String? = null
)

data class CurrentSeason(
	val winner: Any? = null,
	val currentMatchday: Int? = null,
	val endDate: String? = null,
	val id: Int? = null,
	val startDate: String? = null
)

