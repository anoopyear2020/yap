package org.yapframework;

/**
 * Used to implement custom getters and setters for model properties
 * @param <P> The type of the parameter expected by the setter
 * @param <R> The type returned by the getter
 */
public interface PropertyProxy<P,R> {
    public R get(Model model);
    public void set(Model model, P value);
}
