package config;

public record VerifierConfig(
        boolean enabled,
        boolean headless,
        String visualizerPath,
        String inputLocator,
        String outputLocator,
        String statusLocator,
        String infoLocator
) {
}
