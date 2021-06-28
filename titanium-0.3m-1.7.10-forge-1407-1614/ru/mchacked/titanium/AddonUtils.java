package ru.mchacked.titanium;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class AddonUtils {
	
	private ArrayList Field100 = new ArrayList();
	private int modCount = 0;
	
	public void clear() {
		this.Field100.clear();
		++this.modCount;
	}
	
	public void add(Object e) {
		this.Field100.add(e);
		++this.modCount;
	}
	
	public void remove(Object e) {
		this.Field100.remove(e);
		++this.modCount;
	}
	
	public Iterable Method127() {
		return new AddonUtils.a(0, 1);
	}
	
	public Iterable Method128() {
		return new AddonUtils.a(this.Field100.size() - 1, -1);
	}
	
	private class a implements Iterable, Iterator {
		
		private int index;
		private int step;
		private int modCount;
		
		public a(int index, int step) {
			this.modCount = AddonUtils.this.modCount;
			this.index = index;
			this.step = step;
		}
		
		@Override
		public Iterator iterator() {
			return this;
		}
		
		@Override
		public boolean hasNext() {
			return this.step < 0?this.index >= 0:this.index < AddonUtils.this.Field100.size();
		}
		
		@Override
		public Object next() {
			if(this.modCount != AddonUtils.this.modCount) {
				throw new ConcurrentModificationException();
			} else {
				Object ret = AddonUtils.this.Field100.get(this.index);
				this.index += this.step;
				return ret;
			}
		}
		
		@Override
		public void remove() {
			AddonUtils.this.Field100.remove(this.index);
			if(this.step < 0) {
				--this.index;
			}
			
			++this.modCount;
		}
		
	}
	
}
