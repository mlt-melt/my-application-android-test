# ⚡ Быстрая справка: Android UI Разработка

## 1️⃣ ПРОГРАММНОЕ СОЗДАНИЕ UI (Java код)

### LinearLayout
```java
LinearLayout layout = new LinearLayout(this);
layout.setOrientation(LinearLayout.VERTICAL); // или HORIZONTAL
layout.setBackgroundColor(0xFFFFFFFF);
layout.setPadding(16, 16, 16, 16);

// Параметры для children
LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
);
params.bottomMargin = 8;
view.setLayoutParams(params);

layout.addView(view);
setContentView(layout);
```

### TextView
```java
TextView tv = new TextView(this);
tv.setText("Текст");
tv.setTextSize(16);
tv.setTextColor(0xFF000000);
tv.setBackgroundColor(0xFFFFFFFF);
tv.setPadding(12, 12, 12, 12);
tv.setGravity(Gravity.CENTER);
```

### Получение размера экрана
```java
DisplayMetrics displayMetrics = new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
int widthPx = displayMetrics.widthPixels;
int heightPx = displayMetrics.heightPixels;
float density = displayMetrics.density;
int widthDp = (int)(widthPx / density);
```

### Конвертация px ↔ dp
```java
float density = getResources().getDisplayMetrics().density;
int dpValue = 16;
int pxValue = (int)(dpValue * density); // dp -> px
int dpResult = (int)(pxValue / density); // px -> dp
```

---

## 2️⃣ XML РАЗМЕТКА

### Основная структура
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    
    <TextView
        android:id="@+id/my_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello" />
</LinearLayout>
```

### Размеры элементов
```xml
<!-- match_parent - заполнить родителя -->
android:layout_width="match_parent"

<!-- wrap_content - размер по содержимому -->
android:layout_width="wrap_content"

<!-- Фиксированный размер -->
android:layout_width="200dp"
android:layout_height="100dp"

<!-- Layout weight (линейные параметры) -->
android:layout_weight="1"
```

### Padding (внутри) и Margin (снаружи)
```xml
<!-- Одинаково со всех сторон -->
android:padding="16dp"
android:layout_margin="16dp"

<!-- По сторонам отдельно -->
android:paddingLeft="20dp"
android:paddingTop="10dp"
android:paddingRight="20dp"
android:paddingBottom="10dp"

android:layout_marginLeft="8dp"
android:layout_marginTop="16dp"
android:layout_marginRight="8dp"
android:layout_marginBottom="16dp"
```

---

## 3️⃣ CONSTRAINTLAYOUT

### Пространство имен
```xml
xmlns:app="http://schemas.android.com/apk/res-auto"
```

### Базовые ограничения
```xml
<!-- Привязка к сторонам -->
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintBottom_toBottomOf="parent"

<!-- Привязка к другому элементу -->
app:layout_constraintTop_toBottomOf="@id/other_view"
app:layout_constraintStart_toEndOf="@id/other_view"
app:layout_constraintEnd_toStartOf="@id/other_view"
```

### Центрирование
```xml
<!-- Горизонтальное центрирование -->
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintEnd_toEndOf="parent"

<!-- Вертикальное центрирование -->
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintBottom_toBottomOf="parent"

<!-- Полное центрирование (оба) -->
```

### Bias (сдвиг)
```xml
<!-- Горизонтальный сдвиг (0.0 = левый край, 1.0 = правый край) -->
app:layout_constraintHorizontal_bias="0.75"

<!-- Вертикальный сдвиг (0.0 = верх, 1.0 = низ) -->
app:layout_constraintVertical_bias="0.75"
```

### Цепочки (Chains)
```xml
<!-- Первый элемент цепочки (spread) -->
<TextView
    android:id="@+id/chain_1"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toStartOf="@id/chain_2"
    app:layout_constraintHorizontal_chainStyle="spread" />

<!-- Средний элемент -->
<TextView
    android:id="@+id/chain_2"
    app:layout_constraintStart_toEndOf="@id/chain_1"
    app:layout_constraintEnd_toStartOf="@id/chain_3" />

<!-- Последний элемент -->
<TextView
    android:id="@+id/chain_3"
    app:layout_constraintStart_toEndOf="@id/chain_2"
    app:layout_constraintEnd_toEndOf="parent" />
```

### Стили цепочек
```xml
<!-- spread - распределить с промежутками (по умолчанию) -->
app:layout_constraintHorizontal_chainStyle="spread"

<!-- spread_inside - распределить между элементами -->
app:layout_constraintHorizontal_chainStyle="spread_inside"

<!-- packed - вместе без промежутков -->
app:layout_constraintHorizontal_chainStyle="packed"
```

### Аналогично для вертикальных цепочек
```xml
app:layout_constraintVertical_chainStyle="spread"
app:layout_constraintVertical_chainStyle="packed"
app:layout_constraintVertical_bias="0.5"
```

---

## 4️⃣ PROGRAMMATIC CONSTRAINTLAYOUT (Java)

### Создание
```java
ConstraintLayout layout = new ConstraintLayout(this);
layout.setLayoutParams(new ViewGroup.LayoutParams(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.MATCH_PARENT
));

// Добавляем VIEW (ВАЖНО: установить ID!)
TextView view = new TextView(this);
view.setId(100);
view.setText("Text");
layout.addView(view);

// Добавляем ограничения через ConstraintSet
ConstraintSet cs = new ConstraintSet();

// Привязка к родителю
cs.connect(view.getId(), ConstraintSet.TOP, 
           ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16);
cs.connect(view.getId(), ConstraintSet.START,
           ConstraintSet.PARENT_ID, ConstraintSet.START, 0);

// Привязка к другому элементу
cs.connect(view.getId(), ConstraintSet.TOP,
           other.getId(), ConstraintSet.BOTTOM, 8);

// Применяем ограничения
cs.applyTo(layout);
setContentView(layout);
```

### Цепочки в коде
```java
// Горизонтальная цепочка
int[] chainIds = {item1.getId(), item2.getId(), item3.getId()};
cs.createHorizontalChain(
    ConstraintSet.PARENT_ID, ConstraintSet.START,
    ConstraintSet.PARENT_ID, ConstraintSet.END,
    chainIds, null, ConstraintSet.CHAIN_SPREAD);

// Вертикальная цепочка
cs.createVerticalChain(
    ConstraintSet.PARENT_ID, ConstraintSet.TOP,
    ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM,
    chainIds, null, ConstraintSet.CHAIN_SPREAD);

cs.applyTo(layout);
```

### Bias в коде
```java
cs.setHorizontalBias(view.getId(), 0.75f); // 75% вправо
cs.setVerticalBias(view.getId(), 0.75f);   // 75% вниз
cs.applyTo(layout);
```

---

## 5️⃣ LAYOUT WEIGHTS (LinearLayout)

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    
    <!-- Занимает 1 часть доступного пространства -->
    <View
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="#FF0000" />
    
    <!-- Занимает 3 части -->
    <View
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="3"
        android:background="#00FF00" />
    
    <!-- Занимает 1 часть -->
    <View
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="#0000FF" />
</LinearLayout>
```

---

## 6️⃣ COLORS И СТИЛИ

### Цвета (0xAARRGBB)
```java
0xFFFFFFFF  // Белый
0xFF000000  // Черный
0xFF1565C0  // Синий
0xFF4CAF50  // Зеленый
0xFFFF5722  // Оранжевый
0xFFD32F2F  // Красный
```

### Текст
```java
tv.setTextColor(0xFF000000);
tv.setTextSize(16); // SP
tv.setTextStyle(android.graphics.Typeface.BOLD);
tv.setTypeface(android.graphics.Typeface.MONOSPACE);
tv.setGravity(Gravity.CENTER);
tv.setGravity(Gravity.START | Gravity.TOP);
```

### Фон
```java
view.setBackgroundColor(0xFFFFFFFF);
view.setBackground(getDrawable(R.drawable.my_drawable));
```

---

## 7️⃣ ВАЖНЫЕ АТРИБУТЫ

| Атрибут | Значение | Описание |
|---------|---------|---------|
| `layout_width` | `match_parent` &#124; `wrap_content` &#124; `dimen` | Ширина |
| `layout_height` | то же | Высота |
| `padding` | `dimen` | Внутренний отступ |
| `layout_margin` | `dimen` | Внешний отступ |
| `gravity` | `center`, `start`, `end` | Выравнивание содержимого |
| `layout_gravity` | то же | Выравнивание самого элемента |
| `textSize` | число + `sp` | Размер текста |
| `background` | цвет &#124; drawable | Фон |
| `orientation` | `vertical` &#124; `horizontal` | Направление (LinearLayout) |

---

**Создание UI в Android - ключевые моменты:**
1. Всегда преобразуйте dp ↔ px через density
2. В ConstraintLayout обязательно назначайте ID элементам
3. Используйте `match_parent` для заполнения, `wrap_content` для авто-размера
4. Padding (внутри), Margin (снаружи)
5. Цепочки в ConstraintLayout - мощный инструмент для рядов/столбцов
