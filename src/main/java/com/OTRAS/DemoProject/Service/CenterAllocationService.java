//package com.OTRAS.DemoProject.Service;
//
//import com.OTRAS.DemoProject.Entity.AdmitCardConfig;
//import com.OTRAS.DemoProject.Entity.PaymentSuccesfullData;
//import com.OTRAS.DemoProject.Repository.PaymentSuccesfullDataRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class CenterAllocationService {
//    
//    private final PaymentSuccesfullDataRepository paymentRepository;
//    
//    public Map<String, Object> allocateCentersForJobPost(Long jobPostId, List<Map<String, String>> availableCenters, AdmitCardConfig config) {
//        List<PaymentSuccesfullData> applicants = paymentRepository.findByJobPostIdAndPaymentStatus(jobPostId, "SUCCESS");
//        
//        Map<String, Integer> capacities = availableCenters.stream()
//            .collect(Collectors.toMap(c -> (String) c.get("centerCode"), 
//                                    c -> Integer.parseInt((String) c.getOrDefault("capacity", "200"))));
//        Map<String, Integer> allocations = capacities.keySet().stream().collect(Collectors.toMap(k -> k, k -> 0));
//        
//        applicants.sort(Comparator.comparing(PaymentSuccesfullData::getId)); // FIFO
//        
//        int allocated = 0;
//        Set<String> unallocatedOtrIds = new HashSet<>();
//        
//        for (int pref = 0; pref < 3; pref++) {
//            for (PaymentSuccesfullData app : applicants) {
//                if (app.getAllocatedCenter() != null) continue;
//                
//                List<String> prefs = app.getCenters();
//                if (prefs != null && prefs.size() > pref) {
//                    String prefCenter = prefs.get(pref);
//                    if (prefCenter != null && capacities.containsKey(prefCenter) && allocations.get(prefCenter) < capacities.get(prefCenter)) {
//                        app.setAllocatedCenter(prefCenter);
//                        allocations.put(prefCenter, allocations.get(prefCenter) + 1);
//                        allocated++;
//                    }
//                }
//            }
//        }
//        
//        for (PaymentSuccesfullData app : applicants) {
//            if (app.getAllocatedCenter() == null) {
//                boolean assigned = false;
//                for (Map.Entry<String, Integer> entry : capacities.entrySet()) {
//                    if (allocations.get(entry.getKey()) < entry.getValue()) {
//                        app.setAllocatedCenter(entry.getKey());
//                        allocations.put(entry.getKey(), allocations.get(entry.getKey()) + 1);
//                        allocated++;
//                        assigned = true;
//                        break;
//                    }
//                }
//                if (!assigned) {
//                    unallocatedOtrIds.add(app.getOtrId());
//                }
//            }
//        }
//        
//        // Roll numbers: OTR<jobId 4 digits><seq 4 digits>
//        int seq = 1;
//        for (PaymentSuccesfullData app : applicants) {
//            if (app.getAllocatedCenter() != null) {
//                app.setRollNumber(String.format("OTR%04d%04d", jobPostId, seq++));
//                app.setApplicationStatus("ALLOCATED");
//                app.setExamDate(config.getExamDate());
//                app.setExamTime(config.getExamTime());
//            }
//        }
//        
//        paymentRepository.saveAll(applicants);
//        
//        Map<String, Object> result = new HashMap<>();
//        result.put("totalApplicants", applicants.size());
//        result.put("allocated", allocated);
//        result.put("unallocatedOtrIds", new ArrayList<>(unallocatedOtrIds));
//        result.put("utilization", allocations);
//        return result;
//    }
//}