# مستندات کامل Java

## مقدمه

Java یک زبان برنامه‌نویسی شی‌گرا، سطح بالا و چندمنظوره است که توسط James Gosling در شرکت Sun Microsystems (اکنون Oracle)
در سال 1995 توسعه یافت. Java با شعار "Write Once, Run Anywhere" (یک بار بنویس، همه جا اجرا کن) طراحی شده است.

## تاریخچه Java

### پیشینه

- **1991**: پروژه Green در Sun Microsystems شروع شد
- **1995**: Java 1.0 منتشر شد
- **1996**: Java 1.1 با ویژگی‌های جدید
- **1998**: Java 2 (J2SE 1.2) - انقلاب در Java
- **2004**: Java 5.0 با Generics و Annotations
- **2011**: Java 7 با ویژگی‌های جدید
- **2014**: Java 8 با Lambda Expressions و Stream API
- **2017**: Java 9 با Modules (Project Jigsaw)
- **2018**: Java 10, 11 (LTS)
- **2019**: Java 12, 13
- **2020**: Java 14, 15
- **2021**: Java 16, 17 (LTS)
- **2022**: Java 18, 19
- **2023**: Java 20, 21 (LTS)

### نسخه‌های LTS (Long Term Support)

- Java 8 (2014)
- Java 11 (2018)
- Java 17 (2021) - استفاده شده در این پروژه
- Java 21 (2023)

## مفاهیم پایه Java

### ساختار یک کلاس Java

```java
package com.reyhan.hotel;

public class Hotel {
    // Fields (فیلدها)
    private Long id;
    private String name;
    
    // Constructor (سازنده)
    public Hotel() {
    }
    
    public Hotel(String name) {
        this.name = name;
    }
    
    // Methods (متدها)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
```

### انواع داده (Data Types)

#### Primitive Types

```java
byte b = 127;           // 8-bit
short s = 32767;        // 16-bit
int i = 2147483647;     // 32-bit
long l = 9223372036854775807L;  // 64-bit
float f = 3.14f;        // 32-bit floating point
double d = 3.14159;     // 64-bit floating point
char c = 'A';           // 16-bit Unicode
boolean bool = true;    // true or false
```

#### Reference Types

```java
String str = "Hello";
Object obj = new Object();
int[] array = new int[10];
List<String> list = new ArrayList<>();
```

### کلاس‌ها و Objects

```java
// تعریف کلاس
public class User {
    // Fields
    private Long id;
    private String firstName;
    private String lastName;
    
    // Constructor
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getter
    public Long getId() {
        return id;
    }
    
    // Setter
    public void setId(Long id) {
        this.id = id;
    }
    
    // Method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

// استفاده
User user = new User("علی", "احمدی");
String fullName = user.getFullName();
```

### Inheritance (ارث‌بری)

```java
// کلاس والد
public class Vehicle {
    protected String brand;
    
    public void start() {
        System.out.println("Vehicle started");
    }
}

// کلاس فرزند
public class Car extends Vehicle {
    private int doors;
    
    @Override
    public void start() {
        System.out.println("Car started");
    }
}
```

### Interfaces

```java
// تعریف Interface
public interface Repository<T> {
    T findById(Long id);
    List<T> findAll();
    void save(T entity);
    void delete(Long id);
}

// پیاده‌سازی
public class UserRepository implements Repository<User> {
    @Override
    public User findById(Long id) {
        // Implementation
    }
    
    // سایر متدها...
}
```

### Generics

```java
// کلاس Generic
public class Box<T> {
    private T content;
    
    public void setContent(T content) {
        this.content = content;
    }
    
    public T getContent() {
        return content;
    }
}

// استفاده
Box<String> stringBox = new Box<>();
Box<Integer> intBox = new Box<>();
```

### Collections

```java
// List
List<String> names = new ArrayList<>();
names.add("علی");
names.add("محمد");
names.get(0); // "علی"

// Set
Set<String> uniqueNames = new HashSet<>();
uniqueNames.add("علی");
uniqueNames.add("علی"); // اضافه نمی‌شود

// Map
Map<String, Integer> ages = new HashMap<>();
ages.put("علی", 25);
ages.get("علی"); // 25

// Stream API (Java 8+)
List<String> filtered = names.stream()
    .filter(name -> name.startsWith("ع"))
    .collect(Collectors.toList());
```

### Exception Handling

```java
try {
    // کد پرخطر
    int result = 10 / 0;
} catch (ArithmeticException e) {
    // مدیریت خطا
    System.err.println("تقسیم بر صفر: " + e.getMessage());
} finally {
    // کد همیشه اجرا می‌شود
    System.out.println("پایان");
}

// Custom Exception
public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(String message) {
        super(message);
    }
}
```

## Java در این پروژه

### Entity Classes

```java
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String city;
    private String address;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();
    
    // Getters and Setters
}
```

### Repository Pattern

```java
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCity(String city);
    Optional<Hotel> findByName(String name);
}
```

### Service Layer

```java
@Service
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
        
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setGuestName(request.getGuestName());
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());
        
        return reservationRepository.save(reservation);
    }
}
```

### Controller (REST API)

```java
@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {
    private final HotelRepository hotelRepository;
    
    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }
    
    @GetMapping
    public List<Hotel> listHotels() {
        return hotelRepository.findAll();
    }
    
    @GetMapping("/{id}/rooms")
    public ResponseEntity<List<Room>> listRooms(
        @PathVariable Long id,
        @RequestParam(required = false) Boolean available,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        // Implementation
    }
}
```

### DTO (Data Transfer Object)

```java
public class ReservationRequest {
    @NotNull
    private Long roomId;
    
    @NotBlank
    private String guestName;
    
    @Email
    private String guestEmail;
    
    @NotNull
    private LocalDate startDate;
    
    @NotNull
    private LocalDate endDate;
    
    // Getters and Setters
}
```

### Annotations استفاده شده

```java
@Entity              // JPA Entity
@Id                  // Primary Key
@GeneratedValue      // Auto-generated ID
@OneToMany          // One-to-Many Relationship
@ManyToOne          // Many-to-One Relationship
@RestController     // REST Controller
@RequestMapping     // URL Mapping
@GetMapping         // GET Request Handler
@PostMapping        // POST Request Handler
@PathVariable       // Path Variable
@RequestParam       // Query Parameter
@RequestBody        // Request Body
@CrossOrigin        // CORS
@Service            // Service Bean
@Repository         // Repository Bean
@NotNull            // Validation
@NotBlank           // Validation
@Email              // Email Validation
```

## مفاهیم پیشرفته

### Lambda Expressions (Java 8+)

```java
// قبل از Java 8
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.compareTo(b);
    }
});

// با Lambda
Collections.sort(names, (a, b) -> a.compareTo(b));

// Method Reference
names.forEach(System.out::println);
```

### Stream API

```java
List<String> filtered = names.stream()
    .filter(name -> name.length() > 3)
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList());
```

### Optional

```java
Optional<Hotel> hotel = hotelRepository.findById(id);

// روش قدیمی
if (hotel.isPresent()) {
    Hotel h = hotel.get();
}

// روش جدید
hotel.ifPresent(h -> System.out.println(h.getName()));

String name = hotel.map(Hotel::getName)
    .orElse("نامشخص");
```

### Records (Java 14+)

```java
public record UserDTO(
    Long id,
    String firstName,
    String lastName
) {
    // به صورت خودکار equals, hashCode, toString ایجاد می‌شود
}
```

### Pattern Matching (Java 17+)

```java
// instanceof Pattern Matching
if (obj instanceof String str) {
    System.out.println(str.length());
}
```

## بهترین روش‌ها (Best Practices)

### 1. Naming Conventions

```java
// Classes: PascalCase
public class HotelController {}

// Methods: camelCase
public void createReservation() {}

// Constants: UPPER_SNAKE_CASE
public static final int MAX_ROOMS = 100;

// Variables: camelCase
String guestName;
```

### 2. Package Structure

```java
com.reyhan.hotel
├── controller    // REST Controllers
├── service       // Business Logic
├── repository    // Data Access
├── entity        // JPA Entities
├── dto           // Data Transfer Objects
└── config        // Configuration
```

### 3. Dependency Injection

```java
// Constructor Injection (پیشنهادی)
public class HotelService {
    private final HotelRepository repository;
    
    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }
}
```

### 4. Exception Handling

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<String> handleNotFound(HotelNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
```

### 5. Validation

```java
@PostMapping("/reservations")
public ResponseEntity<Reservation> createReservation(
    @Valid @RequestBody ReservationRequest request
) {
    // Validation به صورت خودکار انجام می‌شود
}
```

### 6. Logging

```java
private static final Logger logger = LoggerFactory.getLogger(HotelController.class);

public void someMethod() {
    logger.info("Creating reservation for room: {}", roomId);
    logger.error("Error occurred: {}", e.getMessage());
}
```

## Java در این پروژه

### ساختار پکیج

```
com.reyhan.hotel
├── HotelReservationApplication.java  // Main Class
├── config/
│   ├── DataLoader.java              // Initial Data
│   ├── GlobalExceptionHandler.java  // Error Handling
│   └── OpenApiConfig.java           // Swagger Config
├── controller/
│   ├── HotelController.java
│   ├── ReservationController.java
│   └── ...
├── service/
│   ├── ReservationService.java
│   └── ...
├── repository/
│   ├── HotelRepository.java
│   └── ...
├── entity/
│   ├── Hotel.java
│   ├── Room.java
│   └── ...
└── dto/
    ├── ReservationRequest.java
    └── ...
```

### ویژگی‌های استفاده شده

1. **Spring Boot**: فریم‌ورک اصلی
2. **Spring Data JPA**: دسترسی به داده
3. **Dependency Injection**: تزریق وابستگی
4. **REST API**: ارائه سرویس‌های REST
5. **Validation**: اعتبارسنجی ورودی‌ها
6. **Exception Handling**: مدیریت خطاها

## مشکلات رایج و راه‌حل‌ها

### مشکل: NullPointerException

**راه‌حل**:

```java
// استفاده از Optional
Optional<Hotel> hotel = hotelRepository.findById(id);
hotel.ifPresent(h -> processHotel(h));

// استفاده از null check
if (hotel != null) {
    // Process
}
```

### مشکل: Circular Dependency

**راه‌حل**:

- استفاده از Constructor Injection
- استفاده از @Lazy
- بازطراحی ساختار

## منابع بیشتر

برای اطلاعات بیشتر به فایل `REFERENCES.md` مراجعه کنید که شامل لینک‌های مفید به مستندات رسمی Java است.

---

**نکته**: Java یک زبان قدرتمند و بالغ است. همیشه از آخرین ویژگی‌های Java استفاده کنید و کد خود را تمیز و قابل نگهداری
نگه دارید. در این پروژه از Java 17 استفاده شده است که یک نسخه LTS است.

