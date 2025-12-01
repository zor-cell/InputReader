package config;

public record VerificationConfig(
        boolean enabled,
        boolean headless,
        String visualizerPath,
        String inputLocator,
        String outputLocator,
        String statusLocator,
        String infoLocator
) {
}
