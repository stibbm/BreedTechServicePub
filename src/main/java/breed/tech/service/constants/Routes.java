package breed.tech.service.constants;

public class Routes {
    public static final String CREATE_USER = "/createUser";
    public static final String GET_AUTH_TOKEN = "/getAuthToken";
    public static final String CREATE_HORSE = "/createHorse";
    public static final String CREATE_HORSE_SIMPLE = "/createHorseSimple";
    public static final String CREATE_STALL = "/createStall";
    public static final String CREATE_APPOINTMENT = "/createAppointment";
    public static final String CREATE_APPOINTMENTV2 = "/createAppointmentV2";
    public static final String CREATE_LINE_ITEM = "/createLineItem";

    public static final String GET_ALL_USERS = "/getAllUsers";
    public static final String GET_USER_BY_ID = "/getUserById";
    public static final String GET_USER_BY_EMAIL_ADDRESS = "/getUserByEmailAddress";
    public static final String GET_USERS_BY_COMPANY_ID = "/getUsersByCompanyId";
    public static final String GET_USER_BY_AUTH_TOKEN = "/getUserByAuthToken";
    public static final String GET_USERS = "/getUsers";
    public static final String GET_USER_V2 = "/getUserV2";
    public static final String CREATE_USER_V2 = "/createUserV2";
    public static final String GET_CURRENT_USER = "/getCurrentUser";

    public static final String GET_ALL_STALLS = "/getAllStalls";
    public static final String GET_STALLS_BY_COMPANY_ID = "/getStallsByCompanyId";
    public static final String GET_STALL_BY_NAME = "/getStallByName";
    public static final String GET_EMPTY_STALLS = "/getEmptyStalls";

    public static final String GET_ALL_HORSES = "/getAllHorses";
    public static final String GET_HORSES_BY_OWNER_ID = "/getHorsesByOwnerId";
    public static final String GET_HORSE_BY_REGISTRATION_NUMBER = "/getHorseByRegistrationNumber";
    public static final String GET_HORSES_WITH_LOCATION = "/getHorsesWithLocation";

    public static final String GET_HORSE_BY_ID = "/getHorseById";

    public static final String GET_ALL_LINE_ITEMS = "/getAllLineItems";
    public static final String GET_LINE_ITEMS_BY_APPOINTMENT_ID = "/getLineItemsByAppointmentId";

    public static final String GET_ALL_APPOINTMENTS = "/getAllAppointments";
    public static final String GET_APPOINTMENTSV2 = "/getAppointmentsV2";
    public static final String GET_ACTIVE_APPOINTMENTS = "/getActiveAppointments";
    public static final String GET_APPOINTMENTS_BY_HORSE_ID = "/getAppointmentsByHorseId";
    public static final String GET_ACTIVE_APPOINTMENTS_BY_HORSE_ID =
            "/getActiveAppointmentsByHorseId";

    public static final String GET_ALL_COMPANIES = "/getAllCompanies";
    public static final String GET_COMPANY_BY_ID = "/getCompanyById";
    public static final String CREATE_COMPANY = "/createCompany";

    public static final String CREATE_BREEDING_CONTRACT = "/createBreedingContract";
    public static final String GET_ALL_BREEDING_CONTRACTS = "/getAllBreedingContracts";
    public static final String GET_BREEDING_CONTRACTS_BY_USER_ID = "/getBreedingContractsByUserId";
    public static final String CREATE_BREEDING_CONTRACT_STRING = "/createBreedingContractString";
    public static final String GET_BREEDING_CONTRACT_BY_ID = "/createBreedingContractById";

    public static final String CREATE_HORSE_LOCATION = "/createHorseLocation";
    public static final String GET_HORSE_LOCATION_BY_ID = "/getHorseLocationById";
    public static final String GET_HORSE_LOCATION_BY_HORSE_ID = "/getHorseLocationByHorseId";
    public static final String GET_CURRENT_HORSE_LOCATION_BY_HORSE_ID =
            "/getCurrentHorseLocationByHorseId";
    public static final String GET_ALL_HORSE_LOCATIONS = "/getAllHorseLocations";
    public static final String GET_ALL_CURRENT_HORSE_LOCATIONS = "/getAllCurrentHorseLocations";
    public static final String GET_HORSE_V2_BY_ID = "/getHorseV2ById";

    public static final String GET_ALL_STALLS_OCCUPANCY = "/getAllStallsOccupancy";
    public static final String GET_EMPTY_STALLS_OCCUPANCY = "/getEmptyStallsOccupancy";
    public static final String MOVE_HORSE = "/moveHorse";

    public static final String QUICK_BOOKS_WEBHOOK_ACTION = "/quickBooksWebhook";

    public static final String CREATE_QUICKBOOKS_ITEM = "/createQuickbooksItem";
    public static final String CREATE_QUICKBOOKS_CUSTOMER = "/createQuickbooksCustomer";
    public static final String CREATE_QUICKBOOKS_INVOICE = "/createQuickbooksInvoice";
    public static final String CREATE_QUICKBOOKS_ITEM_V2 = "/createQuickbooksItemV2";
    public static final String GET_ALL_QUICKBOOKS_CUSTOMERS = "/getAllQuickbooksCustomers";
    public static final String GET_ALL_QUICKBOOKS_INVOICES = "/getAllQuickbooksInvoices";
    public static final String GET_ALL_QUICKBOOKS_ITEMS = "/getAllQuickbooksItems";
    public static final String CREATE_QUICKBOOKS_INVOICE_V2 = "/createQuickbooksInvoiceV2";

    public static final String GET_STALLIONS = "/getStallions";

    public static final String LOG_IN = "/login";



    public static final String CREATE_APPOINTMENT_ACTION = "/createAppointmentAction";
    public static final String GET_ALL_APPOINTMENT_ACTIONS = "/getAllAppointmentActions";
    public static final String GET_APPOINTMENT_ACTIONS_BY_APPOINTMENT_ID = "/getAppointmentActionsByAppointmentId";

    public static final String UPDATE_APPOINTMENT_STATUS = "/updateAppointmentStatus";
    public static final String CREATE_SEMEN_COLLECTION = "/createSemenCollectionLog";

    public static final String CREATE_CHARGE = "/createCharge";
    public static final String CREATE_CHARGE_BY_HORSE_ID = "/createChargeByHorseId";

    public static final String GET_CHARGES = "/getCharges";


    public static final String CREATE_SEMEN_COLLECTION_LOG_BY_HORSE_ID = "/createSemenCollectionLogByHorseId";


    public static final String GET_SEMEN_COLLECTION_LOG_DISPLAYABLES = "/getSemenCollectionLogDisplayables";
    public static final String GET_APPOINTMENT_ACTIONS = "/getAppointmentActions";


    public static final String CREATE_CHARGEV2 = "/createChargeV2";
    public static final String GET_CHARGESV2 = "/getChargesV2";

    public static final String CREATE_STALL_SIMPLE = "/createStallSimple";

    public static final String CREATE_APPOINTMENT_ACTION_TYPE = "/createAppointmentActionType";
    public static final String GET_APPOINTMENT_ACTION_TYPES_BY_COMPANY_ID = "/createAppointmentActionTypeByCompanyId";
    public static final String GET_APPOINTMENT_ACTION_TYPE_BY_ID = "/getAppointmentActionTypeById";


    public static final String GET_APPOINTMENT_ACTION_TYPES_DISPLAYABLE = "/getAppointmentActionTypeDisplayables";

    public static final String CREATE_APPOINTMENT_ACTION_BY_ACTION_TYPE_ID = "/createAppointmentActionByActionTypeId";


    public static final String GET_APPOINTMENTS_V3 = "/getAppointmentsV3";
    public static final String GET_APPOINTMENT_ACTIONS_V3 = "/getAppointmentActionsV3";

    public static final String GET_APPOINTMENT_ACTIONS_BY_APPOINTMENT_ID_V2 = "/getAppointmentActionsByAppointmentIdV2";

    public static final String GET_APPOINTMENT_ACTION_TYPES_BY_COMPANY_ID_V2 = "/getAppointmentActionTypesByCompanyIdV2";

    public static final String GET_HORSES_V2 = "/getHorsesV2";

    public static final String GET_BREEDING_CONTRACTS_BY_HORSE_ID = "/getBreedingContractsByHorseId";
    public static final String GET_BREEDING_CONTRACT_IMAGES_BY_HORSE_ID = "/getBreedingContractImagesByHorseId";
    public static final String GET_BREEDING_CONTRACT_IMAGE_URLS_BY_HORSE_ID = "/getBreedingContractImageUrlsByHorseId";
    public static final String GET_BREEDING_CONTRACT_PATHS_BY_HORSE_ID = "/getBreedingContractPathsByHorseId";

    public static final String HEARTBEAT = "/heartbeat";

    public static final String GET_APPOINTMENT_ACTIONS_BY_HORSE_ID = "/getAppointmentActionsByHorseId";

    public static final String GET_MONTH_DATES = "/getMonthDates";

    public static final String GET_APPOINTMENT_ACTIONS_BY_USER_ID = "/getAppointmentActionsByUserId";
    public static final String GET_APPOINTMENT_ACTIONS_BY_USER_ID_V2 = "/getAppointmentActionsByUserIdV2";
    public static final String GET_ALL_APPOINTMENT_ACTIONS_V3 = "/getAllAppointmentActionsV3";

    public static final String GET_APPOINTMENT_ACTIONS_BY_TIMESTAMP = "/getAppointmentActionsByTimestamp";
    public static final String GET_APPOINTMENT_ACTIONS_BY_DATE = "/getAppointmentActionsByDate";

    //public static final String GET_ALL_APPOINTMENT_ACTIONS = "/getAllAppointmentActions";

    public static final String CREATE_EVENT = "/createEvent";
    public static final String GET_EVENTS = "/getEvents";

    public static final String CREATE_EVENT_WITH_HUMAN_DATE = "/createEventWithHumanDate";

    public static final String GET_CONTRACT_IMAGE = "/getContractImage";

    public static final String CREATE_FILE = "/createFile";
    public static final String GET_FILE = "/getFile";

    public static final String GET_FILES_BY_PARENT = "/getFilesByParent";
    public static final String GET_FILE_BY_PATH = "/getFileByPath";
    public static final String GET_EVENTS_WITH_TIMEZONE = "/getEventsWithTimeZone";
    public static final String CREATE_EVENT_WITH_TIMESTAMP = "/createEventWithTimestamp";

    public static final String CREATE_STRIPE_PAYMENT_LINK = "/createStripePaymentLink";
    public static final String GET_STRIPE_PAYMENT_LINK = "/getStripePaymentLink";

    public static final String CREATE_STRIPE_PRICE = "/createStripePrice";
    public static final String GET_STRIPE_PRICE = "/getStripePrice";

    public static final String CREATE_STRIPE_PRODUCT = "/createStripeProduct";
    public static final String GET_STRIPE_PRODUCT = "/getStripeProduct";

    public static final String STRIPE_WEBHOOK = "/stripeWebhook";
    public static final String STRIPE_CHARGE_SUCCEEDED_WEBHOOK = "/stripeChargeSucceededWebhook";

    public static final String CREATE_PAYMENTV2 = "/createPaymentV2";
    public static final String GET_PAYMENTV2 = "/getPaymentV2";
    public static final String GET_APPOINTMENT_AMOUNT_DUE = "/getAppointmentAmountDue";
    public static final String GET_APPOINTMENT_V2_BY_ID = "/getAppointmentV2ById";
    public static final String GET_HORSES_BY_NAME = "/getHorsesByName";


    public static final String GET_APPOINTMENTS_BY_USER_ID = "/getAppointmentsByUserId";
    public static final String GET_HORSES_WITH_ACTIVE_APPOINTMENT = "/getHorsesWithActiveAppointment";
    public static final String CREATE_APPOINTMENT_WITH_DATE = "/createAppointmentWithDate";

    public static final String UPDATE_HORSE_FAMILY = "/updateHorseFamily";
}