
# Eastwood JUnit

A small wrapper around [Eastwood](https://github.com/jonase/eastwood)
that outputs its results in JUnit format suitable for consumption by Jenkins.

## Usage

Usage is the same as Eastwood itself, but using **eastwood-junit**

```
lein eastwood-junit '{:namespaces [my.project]}'
```

