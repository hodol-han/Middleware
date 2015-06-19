package kr.ac.ajou.lazybones.config;

import java.util.regex.Pattern;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import kr.ac.ajou.lazybones.config.RootConfig.WebPackage;

/**
 * Class for root configuration. Includes DataConfig.
 * @author AJOU
 *
 */
@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackages = { "kr.ac.ajou.lazybones" }, excludeFilters = { @Filter(type = FilterType.CUSTOM, value = WebPackage.class) })
public class RootConfig {
	public static class WebPackage extends RegexPatternTypeFilter {
		public WebPackage() {
			super(Pattern.compile("kr.ac.ajou.lazybones\\.web"));
		}
	}
}
