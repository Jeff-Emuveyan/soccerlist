package com.seamfix.maontestapplication.data.model.response.teams

import com.seamfix.maontestapplication.data.model.common.Area
import com.seamfix.maontestapplication.data.model.common.Filters
import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.data.model.entity.Team

data class TeamsResponse(
	val teams: List<Team>? = null,
	val count: Int? = null,
	val season: Season? = null,
	val competition: Competition? = null,
	val filters: Filters? = null
)

data class Season(
	val currentMatchday: Any? = null,
	val endDate: String? = null,
	val availableStages: List<String?>? = null,
	val id: Int? = null,
	val startDate: String? = null
)

