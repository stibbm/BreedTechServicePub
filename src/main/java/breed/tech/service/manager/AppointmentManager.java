package breed.tech.service.manager;

import breed.tech.service.model.Appointment;
import breed.tech.service.model.User;
import breed.tech.service.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static breed.tech.service.model.enums.AppointmentStatus.CHECKED_IN;

@Service
@Slf4j
public class AppointmentManager {

    private AppointmentRepository appointmentRepository;
    private AuthTokenManager authTokenManager;
    private UserManager userManager;

    @Autowired
    public AppointmentManager(
            AppointmentRepository appointmentRepository,
            AuthTokenManager authTokenManager,
            UserManager userManager
    ) {
        this.appointmentRepository = Validate.notNull(appointmentRepository);
        this.authTokenManager = Validate.notNull(authTokenManager);
        this.userManager = Validate.notNull(userManager);
    }

    public Appointment getAppointmentById(Long id) {
        Appointment appointment = this.appointmentRepository.findById(id).get();
        return appointment;
    }

    public List<Appointment> getActiveAppointmentsByHorseId(Long horseId) {
        List<Appointment> appointmentsByHorseIdList = this.appointmentRepository.findByHorseId(horseId);
        List<Appointment> activeAppointmentsByHorseId = new ArrayList<>();
        for (Appointment appointment : appointmentsByHorseIdList) {
            if (appointment.getAppointmentStatus().equalsIgnoreCase(CHECKED_IN)) {
                activeAppointmentsByHorseId.add(appointment);
            }
        }
        return activeAppointmentsByHorseId;
    }

    public List<Appointment> getActiveAppointmentsByHorseId(Long horseId, String authToken) {
        User requestUser = this.userManager.getUserByAuthToken(authToken);
        List<Appointment> appointmentsList = this.findAppointmentsByHorseId(horseId);
        List<Appointment> activeAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentsList) {
            Long userId = appointment.getUserId();
            User user = userManager.getUserById(userId);
            Long companyId = user.getCompanyId();
            if (!companyId.equals(requestUser.getCompanyId())) {
                continue;
            }
            else {
                if (appointment.getAppointmentStatus().equalsIgnoreCase(CHECKED_IN)) {
                    activeAppointments.add(appointment);
                }
            }
        }
        return appointmentsList;
    }

    public List<Appointment> getActiveAppointmentsByHorseIdUserAuth(Long horseId, String userAuthToken) {
        User requestUser = this.userManager.getUserByAuthToken(userAuthToken);
        List<Appointment> appointmentsList = this.getActiveAppointmentsByHorseId(horseId, userAuthToken);
        List<Appointment> filteredAppointmentsList = new ArrayList<>();
        for (Appointment appointment : appointmentsList) {
            Long appointmentUserId = appointment.getUserId();
            if (appointmentUserId.equals(requestUser.getId())) {
                filteredAppointmentsList.add(appointment);
            }
        }
        return filteredAppointmentsList;
    }

    public List<Appointment> getAppointmentsByHorseId(Long horseId) {
        List<Appointment> appointmentsList = new ArrayList<>();
        Iterable<Appointment> appointmentsIterable = this.appointmentRepository.findAppointmentsByHorseId(horseId);
        for (Appointment appointment : appointmentsIterable) {
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }

    public List<Appointment> getAppointmentsByHorseId(Long horseId, String authToken) {
        User requestedUser = userManager.getUserByAuthToken(authToken);
        List<Appointment> appointmentsList = new ArrayList<>();
        Iterable<Appointment> appointmentsIterable = this.appointmentRepository.findAppointmentsByHorseId(horseId);
        for (Appointment appointment : appointmentsIterable) {
            User user = userManager.getUserById(appointment.getUserId());
            if (user.getCompanyId().equals(requestedUser.getCompanyId())) {
                appointmentsList.add(appointment);
            }
        }
        return appointmentsList;
    }

    public List<Appointment> getAppointmentsByHorseIdUserAuth(Long horseId, String userAuthToken) {
        User requestUser = userManager.getUserByAuthToken(userAuthToken);
        List<Appointment> appointmentsList = this.getAppointmentsByHorseId(horseId, userAuthToken);
        List<Appointment> filteredAppointmentsList = new ArrayList<>();
        for (Appointment appointment: appointmentsList) {
            if (appointment.getUserId().equals(requestUser.getId())) {
                filteredAppointmentsList.add(appointment);
            }
        }
        return filteredAppointmentsList;
    }

    public List<Appointment> getAppointmentsByUserId(Long userId) {
        Iterable<Appointment> appointmentsIterable = this.appointmentRepository.findAppointmentsByUserId(userId);
        List<Appointment> appointmentsList = new ArrayList<>();
        for (Appointment appointment : appointmentsIterable) {
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }

    public List<Appointment> getAppointmentsByUserId(Long userId, String authToken) {
        User requestUser = this.userManager.getUserByAuthToken(authToken);
        Iterable<Appointment> appointmentsIterable = this.appointmentRepository.findAppointmentsByUserId(userId);
        List<Appointment> appointmentsList = new ArrayList<>();
        for (Appointment appointment : appointmentsIterable) {
            User user = this.userManager.getUserById(appointment.getUserId());
            if (user.getCompanyId().equals(requestUser.getCompanyId())) {
                appointmentsList.add(appointment);
            }
        }
        return appointmentsList;
    }

    public List<Appointment> getAppointmentsByUserIdUserAuth(Long userId, String userAuthToken) {
        User requestUser = userManager.getUserByAuthToken(userAuthToken);
        List<Appointment> appointmentsList = this.getAppointmentsByUserId(userId, userAuthToken);
        List<Appointment> filteredAppointmentsList = new ArrayList<>();
        for (Appointment appointment: appointmentsList) {
            if (appointment.getUserId().equals(requestUser.getId())) {
                filteredAppointmentsList.add(appointment);
            }
        }
        return filteredAppointmentsList;
    }

    private List<Appointment> findAppointmentsByHorseId(Long horseId) {
        Iterable<Appointment> appointmentsIterable = this.appointmentRepository.findAppointmentsByHorseId(horseId);
        List<Appointment> appointmentsList = new ArrayList<>();
        for (Appointment appointment : appointmentsIterable) {
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }

    public List<Appointment> getActiveAppointments() {
        List<Appointment> appointmentsList = new ArrayList<>();
        for (Appointment appointment: this.appointmentRepository.findAll()) {
            if (appointment.getAppointmentStatus().equalsIgnoreCase(CHECKED_IN)) {
                appointmentsList.add(appointment);
            }
        }
        return appointmentsList;
    }

    public Map<Long, Appointment> getActiveAppointmentByHorseIdMap() {
        Map<Long, Appointment> activeAppointmentByHorseIdMap = new HashMap<>();
        for (Appointment appointment: this.appointmentRepository.findAll()) {
            if (appointment.getAppointmentStatus().equalsIgnoreCase(CHECKED_IN)) {
                activeAppointmentByHorseIdMap.put(appointment.getHorseId(), appointment);
            }
        }
        return activeAppointmentByHorseIdMap;
    }

}