package com.seamfix.maontestapplication.data.model.response.teams.details

import com.seamfix.maontestapplication.data.model.common.Area
import com.seamfix.maontestapplication.data.model.common.Player
import com.seamfix.maontestapplication.data.model.entity.TeamDetail

data class TeamDetailResponse(
	val area: Area? = null,
	val venue: String? = null,
	val website: String? = null,
	val address: String? = null,
	val crestUrl: String? = null,
	val tla: String? = null,
	val founded: Int? = null,
	val lastUpdated: String? = null,
	val clubColors: String? = null,
	val squad: List<Player>? = null,
	val phone: String? = null,
	val name: String? = null,
	val id: Int,
	val shortName: String? = null,
	val email: String? = null
){
	fun toTeamDetail(): TeamDetail {
		return TeamDetail(area, venue, website, address, crestUrl, tla, founded, lastUpdated,
			clubColors, squad, phone, name, id, shortName, email)
	}
}

