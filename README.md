# AkzuwoRankAPIPlaceholder

Run `mvn clean package` from the project root to build all modules.
The combined jar produced by the `all` module will appear under `all/target/`.

## Placeholder usage

The plugin registers a PlaceholderAPI expansion with the identifier
`akzuwo_rankpoints`. To display a player's points, use the following syntax:

```
%akzuwo_rankpoints_points%
```

This was changed from the previous `%akzuwoextension_rankpoints%` placeholder
to avoid conflicts with other plugins.
