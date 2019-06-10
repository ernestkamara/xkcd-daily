package io.kamara.xkcd.daily.testing

/**
 * This annotation allows us to open some classes for mocking purposes while they are final in
 * release builds.
 * Ref: https://engineering.21buttons.com/mocking-kotlin-classes-with-mockito-the-fast-way-631824edd5ba
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass

/**
 * Annotate a class with [Mockable] if you want it to be extendable in debug builds.
 */
@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class Mockable