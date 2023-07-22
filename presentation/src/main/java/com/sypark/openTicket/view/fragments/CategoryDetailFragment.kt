package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.sypark.openTicket.Common
import com.sypark.openTicket.Preferences
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentCategoryDetailBinding
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.CategoryDetailViewModel
import com.sypark.openTicket.view.CategoryFilterAreaAdapter
import com.sypark.openTicket.view.CategorySortAdapter
import com.sypark.openTicket.view.PagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailFragment :
    BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {

    private val categoryDetailViewModel: CategoryDetailViewModel by viewModels()
    private lateinit var pagingAdapter: PagingAdapter
    private lateinit var categorySortAdapter: CategorySortAdapter
    private lateinit var categoryFilterAreaAdapter: CategoryFilterAreaAdapter
    private val args by navArgs<CategoryDetailFragmentArgs>()

    override fun init(view: View) {

        binding.apply {
            viewModel = categoryDetailViewModel

            categoryDetailViewModel.filterType.observe(viewLifecycleOwner) {
                Log.e("!!!", it.name)
            }





            includeLayoutSort.sortRecyclerview.apply {
                layoutManager = LinearLayoutManager(view.context)
                categorySortAdapter = CategorySortAdapter { position ->
                    onItemClicked(position)
//                    Preferences.sortPosition = position
                }

                categorySortAdapter.submitList(Common.sortList)
                adapter = categorySortAdapter
                categorySortAdapter.setSelectedPosition(1)
            }

            chipArea.apply {
                setOnCloseIconClickListener {
                    setChipFalse(it.context, this, "지역")
                    initFilterArea(it.context)
                }
            }

            includeLayoutFilter.recyclerviewArea.apply {
                layoutManager = LinearLayoutManager(view.context)
                categoryFilterAreaAdapter = CategoryFilterAreaAdapter()
                adapter = categoryFilterAreaAdapter
                categoryFilterAreaAdapter.submitList(Common.areaList)

//                categoryFilterAreaAdapter.setOnItemClickListener {
//                    if (categoryFilterAreaAdapter.selectedList().size == 0) {
//                        includeLayoutFilter.textAreaAll.setTextColor(
//                            ContextCompat.getColor(
//                                this.context,
//                                R.color.black
//                            )
//                        )
//                        includeLayoutFilter.checkboxAreaAll.show()
//                    } else {
//                        includeLayoutFilter.textAreaAll.setTextColor(
//                            ContextCompat.getColor(
//                                this.context,
//                                R.color.gray_B7B7B7
//                            )
//                        )
//                        includeLayoutFilter.checkboxAreaAll.hide()
//                    }
//
//                    categoryDetailViewModel.setFilterAreaList(categoryFilterAreaAdapter.selectedList())
//                }
            }

            includeLayoutFilter.textAreaAll.setOnClickListener {
                initFilterArea(it.context)
            }
        }


//        binding.apply {
//            imgBack.setOnClickListener {
//                findNavController().popBackStack()
//                Preferences.sortPosition = 1
//            }
//
//            sortLayout.setOnClickListener {
//                categoryDetailViewModel.setDetailSortLayoutVisibility(true)
//            }
//
//            includeLayoutSort.imgClose.setOnClickListener {
//                categoryDetailViewModel.setDetailSortLayoutVisibility(false)
//                Preferences.sortPosition = 1
//            }
//
//            includeLayoutSort.btnConfirm.setOnClickListener {
//                categoryDetailViewModel.setDetailSortLayoutVisibility(false)
//                textSort.text = Common.sortList[Preferences.sortPosition].sort
//            }
//
//            imgFilter.setOnClickListener {
//                categoryDetailViewModel.setFilterLayoutVisibility(true)
//            }
//
//            includeLayoutFilter.imgClose.setOnClickListener {
//                categoryDetailViewModel.setFilterLayoutVisibility(false)
//            }
//
//            includeLayoutSort.sortRecyclerview.apply {
//                layoutManager = LinearLayoutManager(view.context)
//                categorySortAdapter = CategorySortAdapter { position ->
//                    onItemClicked(position)
//                    Preferences.sortPosition = position
//                }
//
//                categorySortAdapter.submitList(Common.sortList)
//                adapter = categorySortAdapter
//                categorySortAdapter.setSelectedPosition(1)
//            }
//
//            includeLayoutFilter.recyclerviewArea.apply {
//                layoutManager = LinearLayoutManager(view.context)
//                categoryFilterAreaAdapter = CategoryFilterAreaAdapter()
//                adapter = categoryFilterAreaAdapter
//                categoryFilterAreaAdapter.submitList(Common.categoryDetailAreaList)
//
//                categoryFilterAreaAdapter.setOnItemClickListener {
//                    if (categoryFilterAreaAdapter.selectedList().size == 0) {
//                        includeLayoutFilter.textAreaAll.setTextColor(
//                            ContextCompat.getColor(
//                                this.context,
//                                R.color.black
//                            )
//                        )
//                        includeLayoutFilter.checkboxAreaAll.show()
//                    } else {
//                        includeLayoutFilter.textAreaAll.setTextColor(
//                            ContextCompat.getColor(
//                                this.context,
//                                R.color.gray_B7B7B7
//                            )
//                        )
//                        includeLayoutFilter.checkboxAreaAll.hide()
//                    }
//
//                    categoryDetailViewModel.setFilterAreaList(categoryFilterAreaAdapter.selectedList())
//                }
//            }
//
//        }
//
//
//
//        binding.includeLayoutFilter.btnConfirm.setOnClickListener {
//            Log.e("!!!!", "includeLayoutFilter")
//            setUpObserver()
//
//            if (categoryDetailViewModel.filterPriceData.value.isNullOrEmpty()) {
//                setChipFalse(it.context, binding.chipPrice, "예매가격")
//            } else {
//                setChipTrue(it.context, binding.chipPrice)
//                binding.chipPrice.text = categoryDetailViewModel.filterPriceData.value.toString()
//            }
//
//            if (categoryDetailViewModel.selectedDay.value.isNullOrEmpty()) {
//                setChipFalse(it.context, binding.chipDay, "공연일")
//            } else {
//                setChipTrue(it.context, binding.chipDay)
//                binding.chipDay.text = categoryDetailViewModel.selectedDay.value.toString()
//            }
//
//            binding.includeLayoutFilter.root.hide()
//        }
//
//        binding.apply {
//            chipDay.setOnClickListener {
//                includeLayoutFilter.apply {
//                    radioFilterDay.isChecked = true
//                    root.show()
//                    layoutFilterDay.show()
//
//                    layoutFilterArea.hide()
//                    layoutFilterPrice.hide()
//                    layoutPerformanceState.hide()
//                }
//            }
//
//            chipDay.setOnCloseIconClickListener {
//                setChipFalse(it.context, chipDay, "공연일")
//                initFilterDay()
//            }
//        }
//
//        binding.chipStatus.apply {
//
//            setOnClickListener {
//                binding.includeLayoutFilter.radioFilterStatus.isChecked = true
//                binding.includeLayoutFilter.root.show()
//
//                binding.includeLayoutFilter.layoutFilterArea.hide()
//                binding.includeLayoutFilter.layoutPerformanceState.show()
//                binding.includeLayoutFilter.layoutFilterPrice.hide()
//                binding.includeLayoutFilter.layoutFilterDay.hide()
//            }
//
//            setOnCloseIconClickListener {
//                setChipFalse(it.context, this, "진행상태")
//                initFilterStatus()
//            }
//        }
//
//        binding.includeLayoutFilter.layoutFilterReset.setOnClickListener {
//            initFilterArea(it.context)
//            setChipFalse(it.context, binding.chipArea, "지역")
//
//            initFilterStatus()
//            setChipFalse(it.context, binding.chipStatus, "진행상태")
//
//            initFilterPrice()
//            setChipFalse(it.context, binding.chipPrice, "예매가격")
//
//            initFilterDay()
//            setChipFalse(it.context, binding.chipDay, "공연일")
//        }
//
//        binding.includeLayoutFilter.textAreaAll.setOnClickListener {
//            initFilterArea(it.context)
//        }
//
//        binding.chipPrice.apply {
//            setOnClickListener {
//                binding.includeLayoutFilter.radioFilterPrice.isChecked = true
//                binding.includeLayoutFilter.root.show()
//
//                binding.includeLayoutFilter.layoutFilterArea.hide()
//                binding.includeLayoutFilter.layoutPerformanceState.hide()
//                binding.includeLayoutFilter.layoutFilterPrice.visibility = View.VISIBLE
//                binding.includeLayoutFilter.layoutFilterDay.hide()
//            }
//
//            setOnCloseIconClickListener {
//                setChipFalse(it.context, this, "예매 가격")
//                initFilterPrice()
//            }
//        }
//        binding.includeLayoutFilter.performanceCalendarView.apply {
//
//        }
//
//        binding.includeLayoutFilter.performanceCalendarView.setOnDateChangedListener { widget, date, selected ->
//            date.apply {
//                if (selected) {
//                    val selectedDay = "${date.month}.${date.day}(${
//                        date.date.dayOfWeek.getDisplayName(
//                            TextStyle.NARROW,
//                            Locale.KOREAN
//                        )
//                    })"
//                    categoryDetailViewModel.setSelectedDay(selectedDay)
//                }
//            }
//        }
//        binding.includeLayoutFilter.layoutPlanned.setOnClickListener {
//            categoryDetailViewModel.isPlanedChecked()
//        }
//
//        binding.includeLayoutFilter.textPerformancePlanned.setOnClickListener {
//            categoryDetailViewModel.isPlanedChecked()
//        }
//        binding.includeLayoutFilter.checkboxPlanned.setOnClickListener {
//            categoryDetailViewModel.isPlanedChecked()
//        }
//
//        binding.includeLayoutFilter.layoutDuring.setOnClickListener {
//            categoryDetailViewModel.isDuringChecked()
//        }
//        binding.includeLayoutFilter.textPerformanceDuring.setOnClickListener {
//            categoryDetailViewModel.isDuringChecked()
//        }
//        binding.includeLayoutFilter.checkboxDuring.setOnClickListener {
//            categoryDetailViewModel.isDuringChecked()
//        }
//
//        binding.includeLayoutFilter.layoutFinish.setOnClickListener {
//            categoryDetailViewModel.isFinishedChecked()
//        }
//
//        binding.includeLayoutFilter.textPerformanceFinish.setOnClickListener {
//            categoryDetailViewModel.isFinishedChecked()
//        }
//        binding.includeLayoutFilter.checkboxFinish.setOnClickListener {
//            categoryDetailViewModel.isFinishedChecked()
//        }
//
//        val statusList = ArrayList<String>()
//
//        categoryDetailViewModel.isPlaned.observe(this) {
//            binding.includeLayoutFilter.textPerformancePlanned.isSelected = it
//            binding.includeLayoutFilter.checkboxPlanned.isSelected = it
//            if (it) {
//                statusList.add(binding.includeLayoutFilter.textPerformancePlanned.text.toString())
//                categoryDetailViewModel.setFilterStatus(statusList)
//            } else {
//                statusList.remove(binding.includeLayoutFilter.textPerformancePlanned.text.toString())
//                categoryDetailViewModel.setFilterStatus(statusList)
//            }
//        }
//
//        categoryDetailViewModel.isFinished.observe(this) {
//            binding.includeLayoutFilter.textPerformanceFinish.isSelected = it
//            binding.includeLayoutFilter.checkboxFinish.isSelected = it
//
//            if (it) {
//                statusList.add(binding.includeLayoutFilter.textPerformanceFinish.text.toString())
//                categoryDetailViewModel.setFilterStatus(statusList)
//            } else {
//                statusList.remove(binding.includeLayoutFilter.textPerformanceFinish.text.toString())
//                categoryDetailViewModel.setFilterStatus(statusList)
//            }
//        }
//
//        categoryDetailViewModel.isDuring.observe(this) {
//            binding.includeLayoutFilter.textPerformanceDuring.isSelected = it
//            binding.includeLayoutFilter.checkboxDuring.isSelected = it
//
//            if (it) {
//                statusList.add(binding.includeLayoutFilter.textPerformanceDuring.text.toString())
//                categoryDetailViewModel.setFilterStatus(statusList)
//            } else {
//                statusList.remove(binding.includeLayoutFilter.textPerformanceDuring.text.toString())
//                categoryDetailViewModel.setFilterStatus(statusList)
//            }
//        }
//
//
//        binding.includeLayoutFilter.radioGroupFilterPrice.setOnCheckedChangeListener { group, checkedId ->
//            when (checkedId) {
//                R.id.radio_filter_price_1 -> {
//                    categoryDetailViewModel.setFilterPrice("1만원 미만")
//                }
//
//                R.id.radio_filter_price_4 -> {
//                    categoryDetailViewModel.setFilterPrice("1~4만원")
//                }
//
//                R.id.radio_filter_price_7 -> {
//                    categoryDetailViewModel.setFilterPrice("4~7만원")
//                }
//
//                R.id.radio_filter_price_10 -> {
//                    categoryDetailViewModel.setFilterPrice("7~10만원")
//                }
//
//                R.id.radio_filter_price_over -> {
//                    categoryDetailViewModel.setFilterPrice("10만원 이상")
//                }
//            }
//        }
//
//        binding.recyclerviewTicket.apply {
//            layoutManager = LinearLayoutManager(view.context)
//            pagingAdapter = PagingAdapter {
//                itemClicked(it.id)
//            }
//
//            adapter = pagingAdapter
//        }
//
//        lifecycleScope.launch {
//            categoryDetailViewModel.setGenre(args.item).collectLatest {
//                try {
//                    Log.e("!!!!!!!!!!", it.toString())
//                    pagingAdapter.submitData(it)
//                } catch (e: Exception) {
//                    Log.e("!!!", e.toString())
//                }
//            }
//        }
//
//        backPressed()
    }

    override fun backPressed() {
//        if (binding.includeLayoutFilter.root.visibility == View.VISIBLE || binding.includeLayoutSort.root.visibility == View.VISIBLE) {
//            binding.includeLayoutFilter.root.visibility = View.GONE
//            binding.includeLayoutSort.root.visibility = View.GONE
//        } else {
//            findNavController().popBackStack()
//        }
    }

    private fun onItemClicked(position: Int) {
        categorySortAdapter.setSelectedPosition(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Preferences.sortPosition = 1
    }

    //    private fun setTextViewSize(textView: TextView, data: String) {
//        // 높이와 너비를 WRAP_CONTENT로 설정합니다.
//        textView.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//
//        textView.text = data
//
//        // 새로운 크기를 설정합니다.
//        textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//        val width = textView.measuredWidth
//        val height = textView.measuredHeight
//
//        textView.layoutParams = LinearLayout.LayoutParams(width, height)
//    }
//
//    private fun setChipTrue(context: Context, chip: Chip) {
//        chip.chipBackgroundColor =
//            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black_111111))
//        chip.chipStrokeColor =
//            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black_111111))
//        chip.isCloseIconVisible = true
//        chip.setTextColor(ContextCompat.getColor(context, R.color.white))
//    }
//
//    private fun setChipFalse(context: Context, chip: Chip, text: String) {
//        chip.chipBackgroundColor =
//            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
//        chip.chipStrokeColor =
//            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray_EFEFEF))
//        chip.isCloseIconVisible = false
//        chip.setTextColor(ContextCompat.getColor(context, R.color.gray_949494))
//        chip.text = text
//    }
//
//
//    private fun setChipTrue1(chip: Chip) {
//        context?.let {
//            chip.chipBackgroundColor =
//                ColorStateList.valueOf(ContextCompat.getColor(it, R.color.black_111111))
//            chip.chipStrokeColor =
//                ColorStateList.valueOf(ContextCompat.getColor(it, R.color.black_111111))
//            chip.setTextColor(ContextCompat.getColor(it, R.color.white))
//        }
//
//        chip.isCloseIconVisible = true
//    }
//
//    private fun setChipFalse1(chip: Chip, text: String) {
//        context?.let {
//            chip.chipBackgroundColor =
//                ColorStateList.valueOf(ContextCompat.getColor(it, R.color.white))
//            chip.chipStrokeColor =
//                ColorStateList.valueOf(ContextCompat.getColor(it, R.color.gray_EFEFEF))
//            chip.setTextColor(ContextCompat.getColor(it, R.color.gray_949494))
//        }
//
//        chip.isCloseIconVisible = false
//        chip.text = text
//    }
//
//    // 필터 지역 초기화
    @SuppressLint("NotifyDataSetChanged")
    private fun initFilterArea(context: Context) {
        binding.apply {
            includeLayoutFilter.apply {
                recyclerviewArea.removeAllViewsInLayout()
                textAreaAll.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.black
                    )
                )
                checkboxAreaAll.show()
            }
            categoryFilterAreaAdapter.apply {
                clear()
                submitList(Common.areaList)
                notifyDataSetChanged()
            }

            categoryDetailViewModel.filterAreaData.value?.clear()
        }
    }

    //
//    // 필터 진행상태 초기화
//    private fun initFilterStatus() {
//        binding.includeLayoutFilter.checkboxDuring.isSelected = false
//        binding.includeLayoutFilter.checkboxPlanned.isSelected = false
//        binding.includeLayoutFilter.checkboxFinish.isSelected = false
//        binding.includeLayoutFilter.textPerformanceDuring.isSelected = false
//        binding.includeLayoutFilter.textPerformancePlanned.isSelected = false
//        binding.includeLayoutFilter.textPerformanceFinish.isSelected = false
//        categoryDetailViewModel.statusList.value?.clear()
//    }
//
//    private fun initFilterPrice() {
//        binding.includeLayoutFilter.radioFilterPriceAll.isChecked = true
//        binding.includeLayoutFilter.radioFilterPrice1.isChecked = false
//        binding.includeLayoutFilter.radioFilterPrice4.isChecked = false
//        binding.includeLayoutFilter.radioFilterPrice7.isChecked = false
//        binding.includeLayoutFilter.radioFilterPrice10.isChecked = false
//        binding.includeLayoutFilter.radioFilterPriceOver.isChecked = false
//        categoryDetailViewModel.clearFilterPrice()
//    }
//
//    private fun initFilterDay() {
//        categoryDetailViewModel.clearSelectedDay()
//        binding.includeLayoutFilter.performanceCalendarView.clearSelection()
//    }
//
//    private fun itemClicked(id: String) {
//        findNavController().navigate(
//            CategoryDetailFragmentDirections.actionCategoryDetailFragmentToTicketDetailFragment(
//                id
//            )
//        )
//    }
//
//    private fun setUpObserver() {
//        categoryDetailViewModel.filterAreaData.observe(viewLifecycleOwner, ::isFilterAreaWatcher)
//        categoryDetailViewModel.statusList.observe(viewLifecycleOwner, ::isFilterChipWatcher)
//    }
//
//    private fun isFilterAreaWatcher(list: ArrayList<CategoryDetailArea>) {
//        if (list.isEmpty()) {
//            setChipFalse1(binding.chipArea, "지역")
//        } else {
//            setChipTrue1(binding.chipArea)
//
//            when (list.size) {
//                0 -> {
//                    binding.chipArea.text = "전체"
//                }
//
//                1 -> {
//                    binding.chipArea.text =
//                        categoryDetailViewModel.filterAreaData.value!![0].area
//                }
//
//                else -> {
//                    binding.chipArea.text =
//                        "지역 ${categoryDetailViewModel.filterAreaData.value?.size}"
//                }
//            }
//        }
//    }
//
//    private fun isFilterChipWatcher(list: ArrayList<String>) {
//        if (list.isEmpty()) {
//            setChipFalse1(binding.chipStatus, "진행 상태")
//        } else {
//            setChipTrue1(binding.chipStatus)
//            binding.chipStatus.text =
//                StringUtil.join(list, ", ").toString()
//        }
//    }
    private fun setChipFalse(context: Context, chip: Chip, text: String) {
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
        chip.chipStrokeColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray_EFEFEF))
        chip.isCloseIconVisible = false
        chip.setTextColor(ContextCompat.getColor(context, R.color.gray_949494))
        chip.text = text
    }
}


