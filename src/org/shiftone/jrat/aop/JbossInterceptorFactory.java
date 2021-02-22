package org.shiftone.jrat.aop;

import org.jboss.aop.Advisor;
import org.jboss.aop.Interceptor;
import org.jboss.aop.InterceptorFactory;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class JbossInterceptorFactory
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.3 $
 */
public class JbossInterceptorFactory implements InterceptorFactory
{
	private static final Log  LOG = LogFactory.getLogger(JbossInterceptorFactory.class);   

	private static final Interceptor INSTANCE = new JbossInterceptor();
	
	public Interceptor create(Advisor advisor)
	{						
		return INSTANCE;
	}

}
