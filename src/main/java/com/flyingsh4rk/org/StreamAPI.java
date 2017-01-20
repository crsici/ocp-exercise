package com.flyingsh4rk.org;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.*;
import java.util.stream.*;


/**
 * Created by pthanhtrung on 10/10/2016.
 */
public class StreamAPI {
    public static void main(String args[]) {
        System.out.println("Chapter 4: Using Stream API ");
        System.out.println("Create Source");

        System.out.println(">> Empty Stream");
        Stream<Integer> source01 = Stream.empty();
        source01.forEach(System.out::println); // Nothing is printed out

        IntStream.empty();
        DoubleStream.empty();
        LongStream.empty();
        try {
            Integer parsed = Optional.<String>empty().map(s -> Integer.parseInt(s)).get();
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }

        System.out.println(">> Finite Stream");

        Stream.of(1, 2, 3, 4).forEach(System.out::println);
        IntStream.of(1, 2, 3, 4).forEach(System.out::println);
        DoubleStream.of(1, 2, 3, 4).forEach(System.out::println);
        LongStream.of(1, 2, 3, 4).forEach(System.out::println);

        System.out.println(">> Infinite Stream");

        Stream.generate(() -> "Hello").limit(2).forEach(System.out::println);
        Stream.iterate(0, n -> n + 1).limit(2).forEach(System.out::println);

        IntStream.generate(() -> 100).limit(2).forEach(System.out::println);
        IntStream.iterate(0, i -> i + 2).limit(2).forEach(System.out::println);

        DoubleStream.generate(() -> 0.5).limit(2).forEach(System.out::println);
        DoubleStream.iterate(1, d -> d + 0.5).limit(2).forEach(System.out::println);

        LongStream.generate(() -> 2).limit(2).forEach(System.out::println);
        LongStream.iterate(1L, l -> l).limit(3).forEach(System.out::println);

        System.out.println("Using terminal operation");

        System.out.println(Stream.of(1, 2, 3).count());
        //Hang
        //System.out.println(Stream.generate(()->1).count());

        Stream.of(1, 2, 3).min((i1, i2) -> i1.compareTo(i2)).ifPresent(System.out::println);
        Stream.of(1, 2, 3).max((i1, i2) -> i1.compareTo(i2)).ifPresent(System.out::println);

        Stream.of(1, 2, 3).findAny().ifPresent(System.out::println);
        System.out.println("Infinite stream findAny");
        Stream.iterate(1, i -> i++).findAny().ifPresent(System.out::println);
        Stream.iterate(1, i -> i++).findFirst().ifPresent(System.out::println);
        System.out.println("anyMatch/allMatch/noneMatch - *only anyMatch can survive in infinite stream");
        System.out.println(Stream.of("1", "2", "Hello").anyMatch(s -> Character.isLetter(s.charAt(0))));
        System.out.println(Stream.of("1", "2", "Hello").allMatch(s -> Character.isLetter(s.charAt(0))));
        System.out.println(Stream.of("1", "2", "Hello").noneMatch(s -> Character.isLetter(s.charAt(0))));


        Stream.iterate("''", s -> s + "+").limit(2).forEach(System.out::println);
        System.out.println(Stream.iterate("", s -> s + "+").anyMatch(s -> s.length() == 0));

        System.out.println("reduce");
        System.out.println("Has three variants :");
        System.out.println("Initial value");
        System.out.println("None initial value");
        System.out.println("+ combiner");
        Stream.of("H", "E", "L", "L", "O").reduce(String::concat).ifPresent(System.out::println);
        System.out.println("With initial value there is always exist value. Don't need to return Optional");
        System.out.println(Stream.of("H", "E", "L", "L", "O").reduce("+", String::concat));
        System.out.println(Stream.of("H", "E", "L", "L", "O").reduce("+", String::concat, (s1, s2) -> {
            System.out.println("Combiner is called");
            return s1 + s2;
        }));

        System.out.println("Collect");
        Stream.of(2, 1, 5, 3, 4).collect(TreeSet::new, TreeSet::add, TreeSet::addAll).forEach(System.out::println);
        ;
        Stream.of(1, 2, 3, 4).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("Intermediate operators");
        System.out.println("filter");
        Stream.of(1, 2, 3, 4, 5).filter(i -> i % 2 == 0).forEach(System.out::println);
        System.out.println("Distinct - equals");
        Stream.of(1, 2, 3, 4, 5, 1).distinct().collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("Limit");
        Stream.of(1, 2, 3, 4, 5, 1).limit(2).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("skip");
        Stream.of(1, 2, 3, 4, 5, 1).skip(2).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("Map -> Element -> other element");
        Stream.of(1, 2, 3, 4, 5, 1).map(i -> i.toString()).forEach(System.out::println);
        System.out.println("Flat Map element -> Stream");
        System.out.println(Stream.of(1, 2, 3, 4).flatMap(i -> Stream.of(i)).collect(Collectors.toList()));
        System.out.println("Sorted");
        Stream.of(5, 6, 1, 2, 3, 4).sorted().forEach(System.out::println);
        System.out.println("Peek : * be careful for case that you can modify ref Value");
        System.out.println(Stream.of(1, 2, 3, 4).peek(System.out::println).count());

        System.out.println("Put it all together");
        List<String> list = Arrays.asList("Toby", "Anna", "Leroy", "Alex");
        list.stream().filter(s -> s.length() == 4).sorted().collect(Collectors.toList()).forEach(System.out::println);

        Stream.generate(() -> "Elsa")
                .filter(n -> n.length() == 4)
                .limit(2)
                .sorted()
                .forEach(System.out::println);


        System.out.println(Paths.get(".").toAbsolutePath());
        System.out.println(Paths.get(".").toAbsolutePath().getNameCount());
        System.out.println(Paths.get(".").toAbsolutePath().getName(0));
        System.out.println(Paths.get(".").toAbsolutePath().getName(3));
        System.out.println(Paths.get(".").getFileName().toAbsolutePath());
        System.out.println(Paths.get("/land/hippo/harry.happy/").getNameCount());
        System.out.println(Paths.get("/land/hippo/harry.happy/").getFileName());
        System.out.println(Paths.get("D:\\Dev-Cpp").getRoot());
        System.out.println(Paths.get("D:\\Dev-Cpp").toAbsolutePath());
        System.out.println(Paths.get("D:/Dev-Cpp").toFile().exists());
        System.out.println(Paths.get("D:/Dev-Cpp").toAbsolutePath());


        Path path = Paths.get("/mammal/carnivore/raccoon.image");
        System.out.println("Path is: " + path);
        System.out.println("Subpath from 0 to 3 is: " + path.subpath(0, 3));
        System.out.println("Subpath from 1 to 3 is: " + path.subpath(1, 3));
        System.out.println("Subpath from 1 to 2 is: " + path.subpath(1, 2));
        System.out.println("Subpath from 1 to 2 is: " + path.subpath(0, 1));


        Path path3 = Paths.get("E:\\data");
        Path path4 = Paths.get("E:\\user\\home");
        Path relativePath = path3.relativize(path4);
        System.out.println(relativePath);
        System.out.println(path3.resolve(relativePath));
        System.out.println(path3.resolve(relativePath).normalize());

        Stream.of(1, 2, 3);
        Stream.empty();
        Stream.generate(() -> 1);
        Stream.iterate(1, (i) -> i++).limit(10).forEach(System.out::println);
        BinaryOperator<Integer> integerIntegerBinaryOperator = new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return null;
            }
        };

        Stream.empty().noneMatch(s -> true);
        Stream.empty().anyMatch(s -> true);
        Stream.empty().allMatch(s -> true);
        Stream.empty().count();
        Stream.empty().forEach(s -> System.out.println(s));
        Stream.empty().min((s1, s2) -> 1);
        Stream.empty().max((s1, s2) -> 1);
        Stream.of(1, 2, 3, 4).filter(i -> {
            System.out.println("Filter" + i);
            return i < 3;
        }).limit(3)
                .forEach(i-> System.out.println("Pring " + i));

        Stream.of(1,2,3,4,4).skip(3).distinct().peek(s->System.out.print(s)).count();


        Stream.of( new int[]{1,2,3},new int[]{1,2,3}).flatMap(i-> Stream.of(Arrays.asList(i))).forEach(System.out::print);
        System.out.println("----"); System.out.println("----");
        Arrays.asList(new Integer[]{1,2,3},new Integer[]{1,2,3},new Integer[]{1,2,3}).stream().flatMap(i-> Arrays.asList(i).stream()).forEach(System.out::println);
        //Arrays.asList(1,2,3).stream().forEach(System.out::println);
        System.out.println(IntStream.of(1,2,3).sum());
        Stream.of(1, 2,3,4).map(s-> s.toString()).forEach(s-> System.out.println(s.getClass()));
        Optional.of(1L).ifPresent(System.out::println);

        Optional.of(Arrays.asList(1,2,3)).flatMap(new Function<List<Integer>, Optional<Long>>() {
            @Override
            public Optional<Long> apply(List<Integer> integers) {
                return  Optional.of(integers.stream().count());
            }
        }).ifPresent(System.out::println);

        System.out.println(        Stream.of(1,2,3,4).collect(Collectors.groupingBy(i->i.toString()))
        );

        System.out.println(Stream.of(1,2,3,1).collect(Collectors.groupingBy(i-> i+"_Key",Collectors.toList())));


        ZoneId.getAvailableZoneIds().stream().forEach(s-> System.out.println(s + ": " +LocalDateTime.now().atZone(ZoneId.of(s)).getOffset().getTotalSeconds()/Math.pow(60,2)));


        System.out.println(ZonedDateTime.now().toInstant().atZone(ZoneId.of("UTC+7")));
        System.out.println();
        System.out.println(ZoneId.of("UTC").getRules().isDaylightSavings(Instant.now()));
        System.out.println(new Locale("vi","VN"));
        System.out.println(new Locale("VI","vn"));

        System.out.println(new Locale.Builder().setLanguage("en").setRegion("US").build());
        System.out.println(new Locale.Builder().setRegion("US").build());
        new Locale("US");


        System.out.println("Parallel process");
        Stream.iterate(1, i-> ++i).limit(10).parallel().collect(ArrayList::new, (t,u)-> t.add(u), (t,u) ->{ System.out.println("Combiner " + t + u); t.addAll(u); }).forEach(System.out::println);


        Arrays.asList(1,2,3,4,5,6)
                .parallelStream()
                .forEach(s -> System.out.print(s+" "));
        System.out.println("-------------");
        List<Integer> synIntls = new ArrayList();

        Stream.iterate(1,(i)-> ++i).limit(100).parallel().map(i-> {synIntls.add(i+1);  return i+1;}).forEachOrdered(System.out::println);
        System.out.println(synIntls);


        ForkJoinTask<?> forkJoinTask = new CountNumber();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Void abc = forkJoinPool.invoke( new CountNumber());
        System.out.println(abc);

        Deque<Integer> integers = new ArrayDeque<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.push(4);
        System.out.println(integers);
        System.out.println(integers.peek());
        System.out.println(integers);


        Queue<Integer> es = new LinkedList<>();
        es.add(1);
        es.add(2);
        es.add(3);
        es.add(4);
        System.out.println(es);
        System.out.println(es.poll());
        System.out.println(es);
        System.out.println(es.element());
        System.out.println(es.peek());
        System.out.println(es.element());

    }

    public static void createDumpFile(Integer mb, String name) throws FileNotFoundException {
        File  file = new File(name);
        String hello = "Hello World";
        byte[] bytes  = hello.getBytes(StandardCharsets.UTF_8);
        Integer multiple = mb*1024/(bytes.length);
        Integer count = 0;
        try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))){
            while (count <= multiple){
                count++;
                bufferedOutputStream.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
