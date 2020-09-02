//package com.oodles.green.controller;
//
//import com.oodles.green.constant.UserStatus;
//import com.oodles.green.dto.AuthResponse;
//import com.oodles.green.dto.UserLoginDTO;
//import com.oodles.green.master.config.DBContextHolder;
//import com.oodles.green.master.entity.MasterTenant;
//import com.oodles.green.master.repository.MasterTenantRepository;
//import com.oodles.green.master.service.MasterTenantService;
//import com.oodles.green.security.UserTenantInformation;
//import com.oodles.green.util.JwtTokenUtil;
//import com.zaxxer.hikari.HikariDataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.annotation.ApplicationScope;
//
//import javax.sql.DataSource;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthenticationController implements Serializable {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
//
//    private Map<String, String> mapValue = new HashMap<>();
//    private final Map<String, String> userDbMap = new HashMap<>();
//
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private MasterTenantService masterTenantService;
//    
//    @Autowired
//    private MasterTenantRepository masterTenantRepository;
//
//    @PostMapping(value = "/login")
//    public ResponseEntity<?> userLogin(@RequestBody @NotNull UserLoginDTO userLoginDTO) throws AuthenticationException {
//        LOGGER.info("userLogin() method call...");
//        if (null == userLoginDTO.getUserName() || userLoginDTO.getUserName().isEmpty()) {
//            return new ResponseEntity<>("User name is required", HttpStatus.BAD_REQUEST);
//        }
//       String emailId=getEmailID(userLoginDTO);
//        MasterTenant masterTenant = masterTenantRepository.findByEmailId(emailId);
//        if (null == masterTenant || masterTenant.getStatus().toUpperCase().equals(UserStatus.INACTIVE)) {
//            throw new RuntimeException("Please contact service provider.");
//        }
//        loadCurrentDatabaseInstance(masterTenant.getDbName(), userLoginDTO.getUserName());
//        final String token = jwtTokenUtil.generateToken(userLoginDTO.getUserName(),
//                String.valueOf(userLoginDTO.getUserEmailId()));
//        dataBaseProperties(userLoginDTO);
//        System.out.println("token values ::: "+token);
//        return ResponseEntity.ok(new AuthResponse(userLoginDTO.getUserName(), token));
//    }
//
//
//	private String getEmailID(@NotNull UserLoginDTO userLoginDTO) {
//		String email=null;
//		List<MasterTenant>master=masterTenantRepository.findAll();
//		for(MasterTenant masterTenant:master) {
//			System.out.println(masterTenant.getEmailId());
//			String [] masterEmailIdSplit=masterTenant.getEmailId().split("@");
//			System.out.println(masterEmailIdSplit.length);
//			String [] userEmailIdSplit=userLoginDTO.getUserEmailId().split("@");
//			System.out.println(userEmailIdSplit.length);
//			for(int i =0;i<masterEmailIdSplit.length && i<userEmailIdSplit.length;i++) {
//				System.out.println("email id :: "+masterEmailIdSplit[i]+" :: user email :::: "+userEmailIdSplit[i]);
//				if(masterEmailIdSplit[i].equalsIgnoreCase(userEmailIdSplit[i])) {
//					email=masterTenant.getEmailId();
//				}
//			}
//		}
//		return email;
//	}
//
//
//	private void dataBaseProperties(@NotNull UserLoginDTO userLoginDTO) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	private void loadCurrentDatabaseInstance(String databaseName, String userName) {
//        DBContextHolder.setCurrentDb(databaseName);
//        mapValue.put(userName, databaseName);
//    }
//
//    @Bean(name = "userTenantInfo")
//    @ApplicationScope
//    public UserTenantInformation setMetaDataAfterLogin() {
//        UserTenantInformation tenantInformation = new UserTenantInformation();
//        if (mapValue.size() > 0) {
//            for (String key : mapValue.keySet()) {
//                if (null == userDbMap.get(key)) {
//                    //Here Assign putAll due to all time one come.
//                    userDbMap.putAll(mapValue);
//                } else {
//                    userDbMap.put(key, mapValue.get(key));
//                }
//            }
//            mapValue = new HashMap<>();
//        }
//        tenantInformation.setMap(userDbMap);
//        return tenantInformation;
//    }
//}
