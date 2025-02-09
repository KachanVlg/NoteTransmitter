package vstu.isd.notebin.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vstu.isd.notebin.repository.UniqueRangeRepository;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Base62HashGenerator implements HashGenerator {
    private final static String BASE_62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private final UniqueRangeRepository uniqueRangeRepository;

    @Override
    public Stream<String> generateHashes(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than 0");
        }

        List<Long> uniqueRange = uniqueRangeRepository.getNextUniqueRange(amount);

        Stream<Long> uniqueNumStream = amount <= 3_000 ?
                uniqueRange.stream() : uniqueRange.parallelStream();

        return uniqueNumStream
                .map(this::applyBase62Encode);
    }

    private String applyBase62Encode(long num) {
        StringBuilder base62 = new StringBuilder();
        while (num > 0) {
            int remainder = (int)(num % BASE_62_CHARS.length());
            base62.insert(0, BASE_62_CHARS.charAt(remainder));
            num /= BASE_62_CHARS.length();
        }

        return base62.toString();
    }
}