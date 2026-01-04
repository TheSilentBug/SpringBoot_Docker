# مستندات کامل Spring Boot

## مقدمه

Spring Boot یک فریم‌ورک Java است که بر اساس Spring Framework ساخته شده و هدف آن ساده‌سازی توسعه اپلیکیشن‌های Spring است.
Spring Boot با ارائه auto-configuration و convention-over-configuration، زمان توسعه را به طور قابل توجهی کاهش می‌دهد.

## تاریخچه Spring Boot

### پیشینه

- **2002**: Spring Framework توسط Rod Johnson ایجاد شد
- **2012**: Spring Boot به عنوان یک پروژه داخلی شروع شد
- **2014**: Spring Boot 1.0 منتشر شد
- **2016**: Spring Boot 1.4 با Spring Boot CLI
- **2017**: Spring Boot 2.0 با Reactive Programming
- **2019**: Spring Boot 2.2
- **2020**: Spring Boot 2.4
- **2021**: Spring Boot 2.5, 2.6
- **2022**: Spring Boot 2.7, 3.0 (با Java 17+)
- **2023**: Spring Boot 3.1, 3.2
- **2024**: Spring Boot 3.3 (نسخه استفاده شده در این پروژه)

### نسخه‌های مهم

- **Spring Boot 1.x**: نسخه اولیه
- **Spring Boot 2.x**: بهبودهای عمده
- **Spring Boot 3.x**: نیاز به Java 17+، Jakarta EE

## مفاهیم پایه Spring Boot

### ساختار یک پروژه Spring Boot

```
src/
├── main/
│   ├── java/
│   │   └── com/reyhan/hotel/
│   │       └── HotelReservationApplication.java
│   └── resources/
│       └── application.yml
└── test/
    └── java/
```

### کلاس اصلی (Main Class)

```java
@SpringBootApplication
public class HotelReservationApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelReservationApplication.class, args);
    }
}
```

`@SpringBootApplication` شامل:

- `@Configuration`: تعریف Bean ها
- `@EnableAutoConfiguration`: فعال‌سازی auto-configuration
- `@ComponentScan`: اسکن کامپوننت‌ها

### Auto-Configuration

Spring Boot به صورت خودکار:

- دیتابیس را تشخیص می‌دهد و DataSource را پیکربندی می‌کند
- JPA/Hibernate را پیکربندی می‌کند
- Web Server (Tomcat) را راه‌اندازی می‌کند
- و بسیاری دیگر...

### Dependency Injection

```java
// Constructor Injection (پیشنهادی)
@Service
public class HotelService {
    private final HotelRepository repository;
    
    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }
}

// Field Injection (استفاده نکنید)
@Autowired
private HotelRepository repository;

// Setter Injection
private HotelRepository repository;

@Autowired
public void setRepository(HotelRepository repository) {
    this.repository = repository;
}
```

## Spring Boot در این پروژه

### Starter Dependencies

```xml
<!-- Web Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Data JPA Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Validation Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### Configuration (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.MySQL8Dialect

server:
  port: ${PORT:8080}

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
```

### REST Controllers

```java
@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {
    private final HotelRepository hotelRepository;
    
    public HotelController(HotelRepository repository) {
        this.hotelRepository = repository;
    }
    
    @GetMapping
    public List<Hotel> listHotels() {
        return hotelRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotel(@PathVariable Long id) {
        return hotelRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody Hotel hotel) {
        Hotel saved = hotelRepository.save(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
```

### Service Layer

```java
@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    
    public ReservationService(
        ReservationRepository reservationRepository,
        RoomRepository roomRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }
    
    public Reservation createReservation(ReservationRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
            .orElseThrow(() -> new RoomNotFoundException("اتاق یافت نشد"));
        
        // بررسی موجود بودن اتاق
        if (!isRoomAvailable(room.getId(), request.getStartDate(), request.getEndDate())) {
            throw new RoomNotAvailableException("اتاق در این بازه زمانی موجود نیست");
        }
        
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setGuestName(request.getGuestName());
        reservation.setGuestEmail(request.getGuestEmail());
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());
        
        return reservationRepository.save(reservation);
    }
    
    private boolean isRoomAvailable(Long roomId, LocalDate from, LocalDate to) {
        long count = reservationRepository.countOverlappingReservations(
            roomId, from, to
        );
        return count == 0;
    }
}
```

### Repository Layer

```java
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCity(String city);
    Optional<Hotel> findByName(String name);
    List<Hotel> findByCityAndNameContaining(String city, String name);
}

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId " +
           "AND r.id NOT IN " +
           "(SELECT res.room.id FROM Reservation res " +
           "WHERE (res.startDate <= :to AND res.endDate >= :from))")
    List<Room> findAvailableRooms(
        @Param("hotelId") Long hotelId,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to
    );
}
```

### Exception Handling

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(HotelNotFoundException e) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            e.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "خطا در اعتبارسنجی",
            errors,
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
```

### Configuration Classes

```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Hotel Reservation API")
                .version("1.0")
                .description("API for hotel reservation system"));
    }
}
```

### Data Initialization

```java
@Component
public class DataLoader implements CommandLineRunner {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    
    public DataLoader(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }
    
    @Override
    public void run(String... args) {
        if (hotelRepository.count() == 0) {
            // ایجاد داده‌های اولیه
            Hotel hotel = new Hotel();
            hotel.setName("هتل عباسی");
            hotel.setCity("اصفهان");
            hotel.setAddress("خیابان چهارباغ");
            hotelRepository.save(hotel);
        }
    }
}
```

## ویژگی‌های Spring Boot

### 1. Auto-Configuration

Spring Boot به صورت خودکار:

- DataSource را پیکربندی می‌کند
- JPA/Hibernate را تنظیم می‌کند
- Web Server را راه‌اندازی می‌کند
- و بسیاری دیگر...

### 2. Starter Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

شامل تمام وابستگی‌های مورد نیاز.

### 3. Embedded Server

Spring Boot به صورت پیش‌فرض از Tomcat استفاده می‌کند که در JAR گنجانده شده است.

### 4. Actuator

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

برای monitoring و management.

### 5. Profiles

```yaml
# application-dev.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hotel_db_dev

# application-prod.yml
spring:
  datasource:
    url: jdbc:mysql://prod-server:3306/hotel_db
```

استفاده: `--spring.profiles.active=prod`

### 6. Externalized Configuration

```yaml
# application.yml
server:
  port: ${PORT:8080}

spring:
  datasource:
    url: ${DB_URL:jdbc:mysql://localhost:3306/hotel_db}
```

## بهترین روش‌ها (Best Practices)

### 1. Layered Architecture

```
Controller Layer  ->  Service Layer  ->  Repository Layer  ->  Database
```

### 2. استفاده از DTO

```java
// به جای Entity مستقیماً در Controller
public class ReservationRequest {
    @NotNull
    private Long roomId;
    
    @NotBlank
    private String guestName;
    // ...
}
```

### 3. Exception Handling

استفاده از `@ControllerAdvice` برای مدیریت مرکزی خطاها.

### 4. Validation

```java
@PostMapping("/reservations")
public ResponseEntity<Reservation> create(
    @Valid @RequestBody ReservationRequest request
) {
    // Validation به صورت خودکار انجام می‌شود
}
```

### 5. Transaction Management

```java
@Service
@Transactional
public class ReservationService {
    // تمام متدها transactional هستند
}
```

### 6. Logging

```java
private static final Logger logger = LoggerFactory.getLogger(HotelController.class);

logger.info("Creating hotel: {}", hotel.getName());
logger.error("Error occurred: {}", e.getMessage());
```

## مشکلات رایج و راه‌حل‌ها

### مشکل: Port Already in Use

**راه‌حل**:

```yaml
server:
  port: 8081
```

یا تغییر پورت در command line:

```bash
java -jar app.jar --server.port=8081
```

### مشکل: Database Connection Failed

**راه‌حل**:

- بررسی تنظیمات datasource
- بررسی اینکه دیتابیس در حال اجرا است
- بررسی credentials

### مشکل: CORS Error

**راه‌حل**:

```java
@CrossOrigin(origins = "*")
@RestController
public class HotelController {
    // ...
}
```

یا global configuration:

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
```

## منابع بیشتر

برای اطلاعات بیشتر به فایل `REFERENCES.md` مراجعه کنید که شامل لینک‌های مفید به مستندات رسمی Spring Boot است.

---

**نکته**: Spring Boot یک فریم‌ورک قدرتمند است که توسعه اپلیکیشن‌های Java را بسیار ساده می‌کند. در این پروژه از Spring
Boot 3.3.3 استفاده شده است که آخرین نسخه است.

