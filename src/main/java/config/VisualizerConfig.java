package config;

public record VisualizerConfig(
        boolean enabled,
        boolean headless,
        String visualizerPath,
        String inputLocator,
        String outputLocator,
        String statusLocator,
        String infoLocator
) {
}
