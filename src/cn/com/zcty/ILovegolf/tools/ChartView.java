package cn.com.zcty.ILovegolf.tools;

import java.util.ArrayList;

import cn.com.zcty.ILovegolf.model.ChartProp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * customized  chartsView
 * �Զ����״ͼ
 * @author Hogan 2012-11-07 
 *
 */

public class ChartView extends View
{
	private boolean mAa;
	private int mChartsNum;
	private ArrayList<ChartProp> mChartProps;
	private Point mCenterPoint;
	private int mR;
	private float mStartAngle;
	private int mWizardLineLength;
	private int mScreenWidth;
	private int mScreenHeight;
	
	public ChartView(Context context)
	{
		super(context);
		initParams();
	}

	
	public ChartView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initParams();
	}

	
	public ChartView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initParams();
	}

	/**
	 * initial some params 
	 * ��ʼ��Ĭ�ϲ���*
	 */
	private void initParams()
	{
		mAa = true;
		mChartsNum = 1;
		mChartProps = new ArrayList<ChartProp>();
		mCenterPoint = new Point(200, 200);
		mR = 50;
		mStartAngle = 0;
		mWizardLineLength = 10;
		
		WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
		mScreenWidth = wm.getDefaultDisplay().getWidth();     
		mScreenHeight = wm.getDefaultDisplay().getHeight();   
	}

	/**
	 * create charts' property
	 * ������״ͼ������
	 * @param chartsNum charts' number ��״ͼ�ĸ���
	 * @return charts' property's list ��״ͼ���Ե�list
	 */
	public ArrayList<ChartProp> createCharts(int chartsNum)
	{
		mChartsNum = chartsNum;
		createChartProp(chartsNum);
		return mChartProps;
	}
	
	/**
	 * set the first chart's start angle when draw
	 * ���õ�һ�����λ���ʱ����ʼ�Ƕ�
	 * @param startAngle the first chart's start angle ��һ�����λ���ʱ����ʼ�Ƕ�
	 */
	public void setStartAngle(float startAngle)
	{
		mStartAngle  = startAngle;
		invalidate();
	}

	/**
	 * set the view anti alias.
	 * �����Ƿ񿹾�ݡ�
	 * @param aa true means will draw hightly; true ��ζ�Ÿ�������ͼ
	 */
	public void setAntiAlias(boolean aa)
	{
		mAa = aa;
		invalidate();
	}
	
	/**
	 * set chart's center point
	 * ���ñ�״ͼ�����ĵ�
	 * @param centerPoint chart's center point ��״ͼ�����ĵ����
	 */
	public void setCenter(Point centerPoint)
	{
		mCenterPoint = centerPoint;
		invalidate();
	}
	
	/**
	 * set chart's radius
	 * ���ñ�״ͼ�뾶
	 * @param r chart's radius ��״ͼ�İ뾶
	 */
	public void setR(int r)
	{
		mR  = r;
		invalidate();
	}
	
	/**
	 * set wizard line's length
	 * �������ߵĳ��ȡ�б�ŵĺͺ��ŵ���һ��ġ�
	 * @param length line's length ���ߵĳ���
	 */
	public void setWizardLineLength(int length)
	{
		mWizardLineLength = length;
		invalidate();
	}
	
	/**
	 * actually create chartProp objects.
	 * �����������Եķ��� 
	 * @param chartsNum charts' number ��״ͼ�ĸ���
	 */
	private void createChartProp(int chartsNum)
	{
		for(int i = 0; i < chartsNum; i++)
		{
			ChartProp chartProp = new ChartProp(this);
			chartProp.setId(i);
			mChartProps.add(chartProp);
		}
	}
	
	/**
	 * onTouchEvent hanlde the UP action
	 * ����̧���¼�
	 *//*
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		switch (action)
			{
			case MotionEvent.ACTION_UP:
				float x = event.getX();
				float y = event.getY();
				ChartProp clickChart = getUpChartProp(x,y);
				if(clickChart != null)
					Toast.makeText(getContext(), "You clicked Up in " + clickChart.getName(), 3000).show();
				else
					Toast.makeText(getContext(), "You clicked Up Not in any Chart!", 3000).show();
				break;

			default:
				break;
			}
		return true;
	}*/





	/**
	 * get the chartProp when Action_UP happened
	 * ��ȡ��̧��ʱ��������ڵ�charProp
	 * @param x action_up's x  upʱ��x���
	 * @param y action_up's y  upʱ��y���
	 * @return chartProp If equals null, means not in any charts! ����ֵΪnull��˵�������κε������ڡ�
	 */
	private ChartProp getUpChartProp(float x, float y)
	{
		double angle = Math.atan2(y-mCenterPoint.y, x - mCenterPoint.x) * 180 / Math.PI;
		if(angle < 0)
		{
			angle = 360 + angle;
		}
		Log.d("test" , "up angle = " + angle);
		
		ChartProp chartPropPosible = getPosibleChartProp(angle);
		if(chartPropPosible != null && inChartZone(x,y))
		{
			return chartPropPosible;
		}
		
		return null;
	}

	/**
	 * judge if the action X Y in the circle.
	 * �ж�̧��ʱ������Ƿ���Բ�ڡ�
	 * @param x action_up's x  upʱ��x���
	 * @param y action_up's y  upʱ��y���
	 * @return true means in circle. ����ֵΪtrue����ʾ��Բ�ڡ�
	 */
	private boolean inChartZone(float x, float y)
	{
		float a2 = (x - mCenterPoint.x) * (x - mCenterPoint.x);
		float b2 = (y - mCenterPoint.y) * (y - mCenterPoint.y);
		float R2 = mR * mR;
		if(a2 + b2 <= R2)
		{
			return true;
		}
		return false;
	}

	/**
	 * judge if the action_up's angle is in one chartProp 
	 * ���̧��ʱ�ĽǶȣ���ȡ���ܵ�ChartProp
	 * @param angle the action_up's angle ̧��ʱ�ĽǶ�
	 * @return the posible chartProp ���ܵ�charProp����Ϊ��Ҫ�ж��ǲ�����Բ�ڡ�
	 */
	private ChartProp getPosibleChartProp(double angle)
	{
		int size = mChartProps.size();
		for(int i =0 ; i < size; i++)
		{
			ChartProp chartProp = mChartProps.get(i);
			Log.i("test" , "chartProp S angle = " + chartProp.getStartAngle() + ", chartProp E angle = " + chartProp.getEndAngle());
			if((angle > chartProp.getStartAngle() && angle <= chartProp.getEndAngle())
			 ||(angle + 360 > chartProp.getStartAngle() && angle + 360 <= chartProp.getEndAngle()))
			{
				return chartProp;
			}
		}
		return null;
	}

	/**
	 * draw
	 * �������
	 * TODO ����ʱ�����ܻ���ֳ�����Ļ�߶ȵ������������Ҫ��һ�����ơ�
	 * ���Ʒ������ɲο������л�ʡ�ԺŵĴ��벿�֡���305 ~ 373 �У�
	 */
	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);
		
		Paint paint = new Paint();
		paint.setAntiAlias(mAa);
		
		float startAngle = mStartAngle;
		int size = mChartProps.size();
		RectF oval = new RectF(mCenterPoint.x - mR, 
								mCenterPoint.y - mR, 
								mCenterPoint.x + mR, 
								mCenterPoint.y + mR);
		
		for(int i= 0; i < size; i++)
		{
			ChartProp chartProp = mChartProps.get(i);
			
			//drawArc
			paint.setColor(chartProp.getColor());
			float sweepAngle = chartProp.getSweepAngle();
			canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
			
			
			//drawWizardLines -----splash line
			float wizardLineAngle =  (float) ((startAngle + sweepAngle / 2) * Math.PI / 180);
			float deltaR = mR - mWizardLineLength/2;
			double sinAngle =  Math.sin(wizardLineAngle);
			double cosAngle =  Math.cos(wizardLineAngle);
			int deltaXs = (int) (deltaR * cosAngle);
			int deltaYs = (int) (deltaR * sinAngle);
			int deltaXl = (int) (mWizardLineLength * cosAngle);
			int deltaYl = (int) (mWizardLineLength * sinAngle);
			Point lineSplashStart = new Point(mCenterPoint.x + deltaXs, mCenterPoint.y + deltaYs);
			Point lineSplashEnd = new Point(lineSplashStart.x + deltaXl, lineSplashStart.y + deltaYl);
			paint.setColor(Color.WHITE);
			canvas.drawLine(lineSplashStart.x, lineSplashStart.y, lineSplashEnd.x, lineSplashEnd.y, paint);
			
			//drawWizardLines -----horizonal line
			if(lineSplashEnd.x <= mCenterPoint.x) //in left of circle
			{
				canvas.drawLine(lineSplashEnd.x - mWizardLineLength, lineSplashEnd.y, lineSplashEnd.x, lineSplashEnd.y, paint);
			}
			else //in right of circle
			{
				canvas.drawLine(lineSplashEnd.x , lineSplashEnd.y, lineSplashEnd.x + mWizardLineLength, lineSplashEnd.y, paint);
			}
			
			//drawText
			String name = chartProp.getName();
			int nameLen = name.length();
			
			paint.setTextSize(chartProp.getFontSize());
			Rect rect = new Rect();
			paint.getTextBounds(name, 0, nameLen, rect); 
			int nameWidth = rect.width();
			int nameHeight = rect.height();
			
			String slStr = "...";
			paint.getTextBounds(slStr, 0, slStr.length(), rect); 
			int slWidth = rect.width();
			
			if(lineSplashEnd.x <= mCenterPoint.x) //in left of circle
			{
				int endX = lineSplashEnd.x - mWizardLineLength;
				int endY = lineSplashEnd.y;
				if(nameWidth > endX)
				{
					int j = nameLen - 1;
					while(j >= 0)
					{
						String subNameString = name.substring(0, j);
						paint.getTextBounds(subNameString, 0, subNameString.length(), rect); 
						int subNameStrWidth = rect.width();
						if(subNameStrWidth + slWidth <= endX)
						{
							break;
						}
						j--;
					}
					String drawTextString = name.substring(0, j) + slStr;
					paint.getTextBounds(drawTextString, 0, drawTextString.length(), rect); 
					canvas.drawText(drawTextString , endX - rect.width(), endY + nameHeight / 2, paint);
				}
				else 
				{
					
					canvas.drawText(name, endX - nameWidth, endY + nameHeight / 2, paint);
				}
			}
			else //in right of circle
			{
				int endX = lineSplashEnd.x + mWizardLineLength;
				int endY = lineSplashEnd.y;
				if(nameWidth + endX > mScreenWidth)
				{
					int j = nameLen - 1;
					while(j >= 0)
					{
						String subNameString = name.substring(0, j);
						paint.getTextBounds(subNameString, 0, subNameString.length(), rect); 
						int subNameStrWidth = rect.width();
						if(subNameStrWidth + slWidth + endX < mScreenWidth)
						{
							break;
						}
						j--;
					}
					String drawTextString = name.substring(0, j) + slStr;
					paint.getTextBounds(drawTextString, 0, drawTextString.length(), rect); 
					canvas.drawText(drawTextString , endX, endY + nameHeight / 2, paint);
				}
				else 
				{
					canvas.drawText(name, endX, endY + nameHeight / 2, paint);
				}
			}
			
			//add startAngle
			chartProp.setStartAngle(startAngle);
			startAngle += sweepAngle; 
			chartProp.setEndAngle(startAngle);
		}
	}

}
