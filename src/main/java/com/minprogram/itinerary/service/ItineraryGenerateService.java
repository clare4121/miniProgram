package com.minprogram.itinerary.service;

import com.minprogram.itinerary.dto.ItineraryGenerateRequest;
import com.minprogram.itinerary.dto.ItineraryGenerateResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItineraryGenerateService {

    public ItineraryGenerateResponse generate(ItineraryGenerateRequest req) {
        ItineraryGenerateResponse resp = new ItineraryGenerateResponse();
        resp.setDestinationId(req.getDestinationId());
        resp.setDays(req.getDays());
        resp.setTravelMode(req.getTravelMode());
        if (req.getPreferences() != null) {
            resp.setPreferences(req.getPreferences());
        }

        for (int i = 1; i <= req.getDays(); i++) {
            ItineraryGenerateResponse.DayPlan dayPlan = new ItineraryGenerateResponse.DayPlan();
            dayPlan.setDayNo(i);
            dayPlan.setItems(mockItems(i, req.getTravelMode(), req.getPreferences()));
            resp.getPlan().add(dayPlan);
        }
        return resp;
    }

    private List<ItineraryGenerateResponse.PlanItem> mockItems(int dayNo, Integer travelMode, List<String> preferences) {
        List<ItineraryGenerateResponse.PlanItem> list = new ArrayList<>();
        String modeText = travelModeText(travelMode);
        String prefText = (preferences == null || preferences.isEmpty()) ? "综合" : String.join("/", preferences);

        list.add(item("09:00-11:30", "上午景点游览 D" + dayNo, "偏好:" + prefText + "，交通方式:" + modeText));
        list.add(item("12:00-13:30", "午餐与休息 D" + dayNo, "建议选择本地特色餐厅"));
        list.add(item("14:00-17:00", "下午路线打卡 D" + dayNo, "优先同区域，减少通勤"));
        list.add(item("19:00-21:00", "夜间活动 D" + dayNo, "可选夜景/步行街/演出"));
        return list;
    }

    private ItineraryGenerateResponse.PlanItem item(String time, String title, String note) {
        ItineraryGenerateResponse.PlanItem item = new ItineraryGenerateResponse.PlanItem();
        item.setTimeRange(time);
        item.setTitle(title);
        item.setNote(note);
        return item;
    }

    private String travelModeText(Integer travelMode) {
        if (travelMode == null) {
            return "未指定";
        }
        return switch (travelMode) {
            case 1 -> "步行";
            case 2 -> "公共交通";
            case 3 -> "自驾";
            default -> "其他";
        };
    }
}
