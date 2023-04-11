package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.data.db.entity.PlaceDetail
import com.sypark.data.db.entity.TicketDetail
import com.sypark.data.repository.PlaceDetailRepository
import com.sypark.data.repository.TicketDetailRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketDetailViewModel @Inject constructor(
    private val ticketDetailRepository: TicketDetailRepository,
    private val placeDetailRepository: PlaceDetailRepository
) : BaseViewModel() {

    private val _ticketDetail = MutableLiveData<TicketDetail>()
    val ticketDetail: LiveData<TicketDetail> = _ticketDetail

    private val _placeDetail = MutableLiveData<PlaceDetail>()
    val placeDetail: LiveData<PlaceDetail> = _placeDetail

    suspend fun getTicketDetailData(mt20id: String) {
        _ticketDetail.value = ticketDetailRepository.getTicketDetail(mt20id)
    }
    suspend fun getPlaceDetailData(mt10id: String) {
        _placeDetail.value = placeDetailRepository.getPlaceDetail(mt10id)
    }
}