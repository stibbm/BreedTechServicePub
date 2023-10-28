package breed.tech.service.manager;

import breed.tech.service.activity.user.create.message.CreateUserResponse;
import breed.tech.service.activity.user.get.message.*;
import breed.tech.service.model.Password;
import breed.tech.service.model.User;
import breed.tech.service.repository.PasswordRepository;
import breed.tech.service.repository.UserRepository;
import breed.tech.service.utility.BCryptUtility;
import breed.tech.service.utility.RandomTokenGenerator;
import breed.tech.service.utility.TimeUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserManager {

    private UserRepository userRepository;
    private PasswordRepository passwordRepository;
    private BCryptUtility bCryptUtility;
    private TimeUtility timeUtility;
    private AuthTokenManager authTokenManager;
    private RandomTokenGenerator randomTokenGenerator;

    /**
     * Constructor
     * @param userRepository user repository
     * @param passwordRepository password repository
     * @param bCryptUtility bCrypt utility
     * @param timeUtility time utility
     * @param authTokenManager auth token manager
     * @param randomTokenGenerator random token generator
     */
    @Autowired
    public UserManager(
            UserRepository userRepository,
            PasswordRepository passwordRepository,
            BCryptUtility bCryptUtility,
            TimeUtility timeUtility,
            AuthTokenManager authTokenManager,
            RandomTokenGenerator randomTokenGenerator
    ) {
        this.userRepository = Validate.notNull(userRepository);
        this.passwordRepository = Validate.notNull(passwordRepository);
        this.bCryptUtility = Validate.notNull(bCryptUtility);
        this.timeUtility = Validate.notNull(timeUtility);
        this.authTokenManager = Validate.notNull(authTokenManager);
        this.randomTokenGenerator = Validate.notNull(randomTokenGenerator);
    }

    /**
     * Create User if does not exist
     * @param firstName String
     * @param lastName String
     * @param password String
     * @param emailAddress String
     * @param accountType String
     * @param companyId Long
     * @return CreateUserResponse
     */
    public CreateUserResponse createUserIfDoesNotExist(String firstName, String lastName,
                                                       String password, String emailAddress, String accountType, Long companyId) {
        try {
            Validate.notBlank(emailAddress);
            User preExistingUser = this.userRepository.findByEmailAddressIgnoreCase(emailAddress);
            if (preExistingUser != null) {
                CreateUserResponse createUserResponse = CreateUserResponse.builder()
                        .user(preExistingUser)
                        .message("A user with that email address already exists")
                        .build();
                return createUserResponse;
            }
            // else, actually create the user
            String hashedPassword = this.bCryptUtility.hashPassword(password);
            User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .emailAddress(emailAddress)
                    .companyId(companyId)
                    .accountType(accountType)
                    .creationTimestamp(timeUtility.getCurrentUnixTimestamp())
                    .build();
            User createdUser = this.userRepository.save(user);
            Password passwordObject =
                    Password.builder()
                            .userId(createdUser.getId())
                            .hashedPassword(hashedPassword)
                            .creationTimestamp(timeUtility.getCurrentUnixTimestamp())
                            .build();
            passwordObject = this.passwordRepository.save(passwordObject);
            CreateUserResponse createUserResponse =
                    CreateUserResponse.builder()
                            .user(createdUser)
                            .build();
            return createUserResponse;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("failed to create user or find a user with that email address");
            return null;
        }
    }

    /**
     * Get User By Email Address
     * @param emailAddress String
     * @return GetUserByEmailAddressResponse
     */
    public GetUserByEmailAddressResponse getUserByEmailAddress(String emailAddress) {
        try {
            User user = this.userRepository.findByEmailAddressIgnoreCase(emailAddress);
            GetUserByEmailAddressResponse getUserByEmailAddressResponse = GetUserByEmailAddressResponse
                    .builder().user(user).build();
            return getUserByEmailAddressResponse;
        }
        catch (final Exception exception) {
            exception.printStackTrace();
            GetUserByEmailAddressResponse getUserByEmailAddressResponse =
                    GetUserByEmailAddressResponse.builder().user(null).build();
            return getUserByEmailAddressResponse;
        }
    }

    /**
     * Get User By Id
     * @param getUserByIdRequest GetUserByIdRequest
     * @return GetUserByIdResponse
     */
    public GetUserByIdResponse getUserById(GetUserByIdRequest getUserByIdRequest) {
        User user = this.userRepository.findById(getUserByIdRequest.getId()).get();
        GetUserByIdResponse getUserByIdResponse = GetUserByIdResponse.builder()
                .user(user)
                .build();
        return getUserByIdResponse;
    }

    /**
     * Get User By Id
     * @param userId User
     * @return User
     */
    public User getUserById(Long userId) {
        GetUserByIdRequest getUserByIdRequest = GetUserByIdRequest.builder().id(userId).build();
        GetUserByIdResponse getUserByIdResponse = this.getUserById(getUserByIdRequest);
        User user = getUserByIdResponse.getUser();
        return user;
    }

    /**
     * Get Users By Company Id
     * @param companyId Long
     * @return Users List
     */
    public List<User> getUsersByCompanyId(Long companyId) {
        List<User> usersList = this.userRepository.findByCompanyId(companyId);
        return usersList;
    }

    /**
     * Get User By Auth Token
     * @param getUserByAuthTokenRequest GetUserByAuthTokenRequest
     * @return GetUserByAuthTokenResponse
     */
    public GetUserByAuthTokenResponse getUserByAuthToken(GetUserByAuthTokenRequest getUserByAuthTokenRequest) {
        User user = authTokenManager.getUserFromAuthToken(getUserByAuthTokenRequest.getAuthToken());
        GetUserByAuthTokenResponse getUserByAuthTokenResponse = GetUserByAuthTokenResponse.builder()
                .user(user)
                .build();
        return getUserByAuthTokenResponse;
    }

    /**
     * Get User By Auth Token
     * @param authToken String
     * @return User
     */
    public User getUserByAuthToken(String authToken) {
        GetUserByAuthTokenRequest getUserByAuthTokenRequest = GetUserByAuthTokenRequest.builder()
                .authToken(authToken).build();
        GetUserByAuthTokenResponse getUserByAuthTokenResponse =
                this.getUserByAuthToken(getUserByAuthTokenRequest);
        return getUserByAuthTokenResponse.getUser();
    }

    /**
     * Create User
     * @param firstName String
     * @param lastName String
     * @param emailAddress String
     * @param companyId Long
     * @return User
     */
    public User createUser(String firstName, String lastName, String emailAddress, Long companyId)
    {
        String randomPassword = randomTokenGenerator.generateRandomToken();
        CreateUserResponse createUserResponse = createUserIfDoesNotExist(
                firstName,
                lastName,
                randomPassword,
                emailAddress,
                "Customer",
                companyId
        );
        return createUserResponse.getUser();
    }

    /**
     * Get CompanyId By Auth Token
     * @param authToken String
     * @return CompanyId
     */
    public Long getCompanyIdByAuthToken(String authToken) {
        User user = this.getUserByAuthToken(authToken);
        Long companyId = user.getCompanyId();
        return companyId;
    }

    /**
     * Get Users
     * @return Users List
     */
    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>();
        for (User user: this.userRepository.findAll()) {
            usersList.add(user);
        }
        return usersList;
    }

    /**
     * Get Users Map
     * @return Users Map
     */
    public Map<Long, User> getUsersMap() {
        Map<Long, User> usersMap = new HashMap<>();
        for (User user : this.userRepository.findAll()) {
            usersMap.put(user.getId(), user);
        }
        return usersMap;
    }

}